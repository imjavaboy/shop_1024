package com.gbq.controller;


import com.gbq.enums.CouponCategoryEnum;
import com.gbq.service.CouponService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-05
 */
@Api(tags = "优惠券模块")
@RestController
@RequestMapping("/api/coupon/v1")
public class CouponController {

    @Autowired
    private CouponService couponService;


    @ApiOperation("分页查询优惠券")
    @GetMapping("/page_coupon")
    public JsonData pageCouponList(@ApiParam("当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                   @ApiParam("每页显示多少条") @RequestParam(value = "size",defaultValue = "10") int size){
        Map<String, Object> pageCouponActivity = couponService.pageCouponActivity(page, size);

        return JsonData.buildSuccess(pageCouponActivity);
    }

    @ApiOperation("领取优惠券")
    @GetMapping("/add/promotion/{coupon_id}")
    public JsonData addPromotionCoupon(@ApiParam(value = "优惠券id",required = true) @PathVariable("coupon_id") long couponId){

        JsonData jsonData =  couponService.addCoupon(couponId, CouponCategoryEnum.PROMOTION);
        return jsonData;
    }

}

