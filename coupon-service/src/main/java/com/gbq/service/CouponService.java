package com.gbq.service;

import com.gbq.enums.CouponCategoryEnum;
import com.gbq.model.CouponDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbq.request.NewUserCouponRequest;
import com.gbq.util.JsonData;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-05
 */
public interface CouponService extends IService<CouponDO> {

    Map<String,Object> pageCouponActivity(int page,int size);

    JsonData addCoupon(long couponId, CouponCategoryEnum promotion);

    JsonData initUserCoupon(NewUserCouponRequest newUserCouponRequest);
}
