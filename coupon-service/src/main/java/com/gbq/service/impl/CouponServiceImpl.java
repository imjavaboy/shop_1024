package com.gbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gbq.enums.BizCodeEnum;
import com.gbq.enums.CouponCategoryEnum;
import com.gbq.enums.CouponPublishEnums;
import com.gbq.enums.CouponStateEnum;
import com.gbq.exception.BizException;
import com.gbq.interceptor.LoginInterceptor;
import com.gbq.mapper.CouponRecordMapper;
import com.gbq.model.CouponDO;
import com.gbq.mapper.CouponMapper;
import com.gbq.model.CouponRecordDO;
import com.gbq.model.LoginUser;
import com.gbq.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbq.util.CommonUtil;
import com.gbq.util.JsonData;
import com.gbq.vo.CouponVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponRecordMapper couponRecordMapper;

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {

        Page<CouponDO> pageInfo = new Page<CouponDO>(page,size);

        Page<CouponDO> couponDOPage = couponMapper.selectPage(pageInfo, new QueryWrapper<CouponDO>()
                .eq("publish", CouponPublishEnums.PUBLISH)
                .eq("category", CouponCategoryEnum.PROMOTION)
                .orderByDesc("create_time"));
        Map<String,Object> map = new HashMap<>(3);
        map.put("total_record",couponDOPage.getTotal());
        map.put("total_pages",couponDOPage.getPages());
        map.put("current_data",couponDOPage.getRecords().stream().map(obj-> beanProcess(obj)).collect(Collectors.toList()));

        return map;
    }

    private CouponVo beanProcess(CouponDO obj) {
        CouponVo couponVo = new CouponVo();
        BeanUtils.copyProperties(obj,couponVo);
        return couponVo;
    }


    /**
     *  1.查询优惠券是否存在
     *  2.是否可以领取：时间，库存，超过限制
     *  3. 扣减库存
     *  4. 保存领取记录
     * @param
     * @since 2022/11/7
     * @return
     */
    @Override
    public JsonData addCoupon(long couponId, CouponCategoryEnum promotion) {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        CouponDO couponDO = couponMapper.selectOne(new QueryWrapper<CouponDO>()
                .eq("id", couponId)
                .eq("category", promotion.name())
                .eq("publish", CouponPublishEnums.PUBLISH));

        if (couponDO == null) {
            throw new BizException(BizCodeEnum.COUPON_NO_EXITS);
        }

        //检查
        this.checkCoupon(couponDO,loginUser.getId());

        //构建记录
        CouponRecordDO couponRecordDO = new CouponRecordDO();
        BeanUtils.copyProperties(couponDO,couponRecordDO);

        couponRecordDO.setCreateTime(new Date());
        couponRecordDO.setUseState(CouponStateEnum.NEW.name());
        couponRecordDO.setUserId(loginUser.getId());
        couponRecordDO.setUserName(loginUser.getName());
        couponRecordDO.setCouponId(couponId);
        couponRecordDO.setId(null);


        //扣减 TODO
       int rows =  couponMapper.reduceStock(couponId);

       if (1 == rows){
           couponRecordMapper.insert(couponRecordDO);
       }else {
           log.warn("发放优惠券失败");
           throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
       }


        return JsonData.buildSuccess();
    }

    /**
     *  校验是否可以领取优惠券
     * @param
     * @since 2022/11/7
     * @return
     */
    private void checkCoupon(CouponDO couponDO, Long id) {
        if (couponDO == null){
            throw new BizException(BizCodeEnum.COUPON_NO_EXITS);
        }
        if (couponDO.getStock() <= 0) {
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }
//        log.info("{},{},{}",couponDO.getPublish(),CouponPublishEnums.PUBLISH,couponDO.getPublish().equals(CouponPublishEnums.PUBLISH));
        if (!couponDO.getPublish().equals(CouponPublishEnums.PUBLISH.name())){
            throw new BizException(BizCodeEnum.COUPON_GET_FAIL);
        }
        long currentTimestamp = CommonUtil.getCurrentTimestamp();

        long start = couponDO.getStartTime().getTime();
        long endTime = couponDO.getEndTime().getTime();
        if (currentTimestamp < start || currentTimestamp > endTime){
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }
        Integer integer = couponRecordMapper.selectCount(new QueryWrapper<CouponRecordDO>()
                .eq("coupon_id", couponDO.getId())
                .eq("user_id", id));
        if (integer >= couponDO.getUserLimit()){
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }

    }
}
