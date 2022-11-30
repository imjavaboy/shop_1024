package com.gbq.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gbq.enums.BizCodeEnum;
import com.gbq.enums.SendCodeEnum;
import com.gbq.fegin.CouponFeginService;
import com.gbq.interceptor.LoginInterceptor;
import com.gbq.mapper.UserMapper;
import com.gbq.model.LoginUser;
import com.gbq.model.UserDO;
import com.gbq.model.vo.NewUserCouponRequestVo;
import com.gbq.model.vo.UserVo;
import com.gbq.request.UserLoginRequest;
import com.gbq.request.UserRequestRegister;
import com.gbq.service.NotifyService;
import com.gbq.service.UserService;
import com.gbq.util.CommonUtil;
import com.gbq.util.JWTUtil;
import com.gbq.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/2 14:48
 * @Copyright 总有一天，会见到成功
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private CouponFeginService couponFeginService;

    @Autowired
    private NotifyService notifyService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     *
     * 用户注册
     * * 邮箱验证码验证
     * * 密码加密
     * * 账号唯一性检查
     * * 插入数据库
     * * 新注册用户福利发放 TODO
     *
     * @param requestRegister
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRED)
//    @GlobalTransactional
    public JsonData register(UserRequestRegister requestRegister) {
        boolean checkCode = false;
        if (StringUtils.isNotBlank(requestRegister.getMail())) {

           checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER,requestRegister.getMail(),requestRegister.getCode());
        }
        if(!checkCode){
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(requestRegister,userDO);

        userDO.setCreateTime(new Date());
        userDO.setSlogan("人生需要动态规划，学习需要贪心算法");


        userDO.setSecret("$1$"+ CommonUtil.getStringNumRandom(8));

        String cryptPwd = Md5Crypt.md5Crypt(requestRegister.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);

        // 检查唯一性
        if (checkUnique(userDO.getMail())){
            int insert = userMapper.insert(userDO);
            log.info("rows:{},注册成功:{}",insert,userDO.toString());
            //新用户注册成功，初始化信息，发放福利等 TODO
            userRegisterInitTask(userDO);


//            int b = 1/0;
            return JsonData.buildSuccess();
        }else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }

    }

    /**
     *  登录逻辑
     * @param
     * @since 2022/11/3
     * @return
     */
    @Override
    public JsonData login(UserLoginRequest userLoginRequest) {
        List<UserDO> userDOSList = userMapper.selectList(new QueryWrapper<UserDO>().eq("mail", userLoginRequest.getMail()));
        if (null != userDOSList && userDOSList.size() == 1){
            //已注册
            UserDO userDO = userDOSList.get(0);
            String crypt = Md5Crypt.md5Crypt(userLoginRequest.getPwd().getBytes(), userDO.getSecret());

            if (crypt.equals(userDO.getPwd())){
                //成功，返回token
                LoginUser loginUser = LoginUser.builder().build();
                BeanUtils.copyProperties(userDO,loginUser);

                String accessToken = JWTUtil.geneJsonWebToken(loginUser);

//                String refreshToken = UUID.randomUUID().toString();
//                redisTemplate.opsForValue().set(refreshToken,"1",1000* 60*60*24*30);
                return JsonData.buildSuccess(accessToken);
            }else{
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }
        }else{
            //未注册
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
    }

    /**
     *  查看用户详情
     * @param
     * @since 2022/11/4
     * @return
     */
    @Override
    public UserVo findUserDetails() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", loginUser.getId()));
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDO,userVo);
        return userVo;
    }

    /**
     *  //新用户注册成功，初始化信息，发放福利等
     * @param
     * @since 2022/11/2
     * @return
     */
    private void userRegisterInitTask(UserDO userDO) {
        NewUserCouponRequestVo request = new NewUserCouponRequestVo();
        request.setName(userDO.getName());
        request.setUserId(userDO.getId());
        JsonData jsonData = couponFeginService.addNewUserCoupon(request);
        log.info("发放新用户注册优惠券成功，{}",jsonData.toString());
    }


    private boolean checkUnique(String mail) {

        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().eq("mail", mail);
        List<UserDO> userDOList = userMapper.selectList(queryWrapper);
        return userDOList.size() > 0 ? false : true;
    }


}
