package com.gbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gbq.enums.BizCodeEnum;
import com.gbq.enums.CouponStateEnum;
import com.gbq.enums.StockTaskStateEnum;
import com.gbq.exception.BizException;
import com.gbq.interceptor.LoginInterceptor;
import com.gbq.mapper.CouponTaskMapper;
import com.gbq.model.CouponRecordDO;
import com.gbq.mapper.CouponRecordMapper;
import com.gbq.model.CouponTaskDO;
import com.gbq.model.LoginUser;
import com.gbq.request.LockCouponRecordRequest;
import com.gbq.service.CouponRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbq.util.JsonData;
import com.gbq.vo.CouponRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-05
 */
@Service
@Slf4j
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecordDO> implements CouponRecordService {

    @Autowired
    private CouponRecordMapper couponRecordMapper;
    @Autowired
    private CouponTaskMapper couponTaskMapper;

    @Override
    public Map<String, Object> pageCouponRecord(int page, int size) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        Page<CouponRecordDO> objectPage = new Page<>(page, size);
        Page<CouponRecordDO> recordDOPage = couponRecordMapper.selectPage(objectPage, new QueryWrapper<CouponRecordDO>()
                .eq("user_id", loginUser.getId()).orderByDesc("create_time"));
        Map<String,Object> map = new HashMap<>();
        map.put("total_record",recordDOPage.getTotal());
        map.put("total_page",recordDOPage.getPages());
        map.put("current_data",recordDOPage.getRecords().stream().map(obj->beanProcess(obj)).collect(Collectors.toList()));
        return map;
    }

    private CouponRecordVo beanProcess(CouponRecordDO obj) {
        CouponRecordVo couponRecordVo = new CouponRecordVo();
        BeanUtils.copyProperties(obj,couponRecordVo);
        return couponRecordVo;
    }

    @Override
    public CouponRecordVo findById(long recordId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        CouponRecordDO couponRecordDO = couponRecordMapper.selectOne(new QueryWrapper<CouponRecordDO>()
                .eq("id",recordId)
                .eq("user_id",loginUser.getId()));
        if (couponRecordDO == null){
            return null;
        }
      return beanProcess(couponRecordDO);
    }

    @Override
    public JsonData lockCouponRecords(LockCouponRecordRequest recordRequest) {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        String orderOutTradeNo = recordRequest.getOrderOutTradeNo();
        List<Long> lockCouponRecordIds = recordRequest.getLockCouponRecordIds();


        int updateRows = couponRecordMapper.lockUseStateBatch(loginUser.getId(), CouponStateEnum.USED.name(),lockCouponRecordIds);

        List<CouponTaskDO> couponTaskDOList =  lockCouponRecordIds.stream().map(obj->{
            CouponTaskDO couponTaskDO = new CouponTaskDO();
            couponTaskDO.setCreateTime(new Date());
            couponTaskDO.setOutTradeNo(orderOutTradeNo);
            couponTaskDO.setCouponRecordId(obj);
            couponTaskDO.setLockState(StockTaskStateEnum.LOCK.name());
            return couponTaskDO;
        }).collect(Collectors.toList());

        int insertRows = couponTaskMapper.insertBatch(couponTaskDOList);
//
        log.info("优惠券记录锁定updateRows={}",updateRows);
        log.info("新增优惠券记录task insertRows={}",insertRows);


        if(lockCouponRecordIds.size() == insertRows && insertRows==updateRows){
            //发送延迟消息

//            for(CouponTaskDO couponTaskDO : couponTaskDOList){
//                CouponRecordMessage couponRecordMessage = new CouponRecordMessage();
//                couponRecordMessage.setOutTradeNo(orderOutTradeNo);
//                couponRecordMessage.setTaskId(couponTaskDO.getId());
//
//                rabbitTemplate.convertAndSend(rabbitMQConfig.getEventExchange(),rabbitMQConfig.getCouponReleaseDelayRoutingKey(),couponRecordMessage);
//                log.info("优惠券锁定消息发送成功:{}",couponRecordMessage.toString());
//            }
//

            return JsonData.buildSuccess();
        }else {

            throw new BizException(BizCodeEnum.COUPON_RECORD_LOCK_FAIL);
        }

    }
}
