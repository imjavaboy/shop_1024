package com.gbq.fegin;


import com.gbq.model.vo.NewUserCouponRequestVo;
import com.gbq.util.JsonData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/18 15:02
 * @Copyright 总有一天，会见到成功
 */

@FeignClient(name = "xdclass-coupon-service")
public interface CouponFeginService {


    /**
     *  新用户注册发放优惠券
     * @param 
     * @since 2022/11/18
     * @return 
     */
    @PostMapping("/api/coupon/v1/new_user_coupon")
    public JsonData addNewUserCoupon( @RequestBody NewUserCouponRequestVo newUserCouponRequest);
}
