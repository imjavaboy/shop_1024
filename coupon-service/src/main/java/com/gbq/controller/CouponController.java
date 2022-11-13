package com.gbq.controller;


import com.gbq.enums.CouponCategoryEnum;
import com.gbq.request.NewUserCouponRequest;
import com.gbq.service.CouponService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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
@Slf4j
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private RedissonClient redissonClient;


    @ApiOperation("分页查询优惠券")
    @GetMapping("/page_coupon")
    public JsonData pageCouponList(@ApiParam("当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                   @ApiParam("每页显示多少条") @RequestParam(value = "size",defaultValue = "20") int size){
        Map<String, Object> pageCouponActivity = couponService.pageCouponActivity(page, size);

        return JsonData.buildSuccess(pageCouponActivity);
    }

    @ApiOperation("领取优惠券")
    @GetMapping("/add/promotion/{coupon_id}")
    public JsonData addPromotionCoupon(@ApiParam(value = "优惠券id",required = true) @PathVariable("coupon_id") long couponId){

        JsonData jsonData =  couponService.addCoupon(couponId, CouponCategoryEnum.PROMOTION);
        return jsonData;
    }

    @ApiOperation("RPC_新用户注册发放优惠券")
    @PostMapping("/new_user_coupon")
    public JsonData addNewUserCoupon(@ApiParam("用户对象") @RequestBody NewUserCouponRequest newUserCouponRequest){
       JsonData jsonData =  couponService.initUserCoupon(newUserCouponRequest);
       return jsonData;

    }


/**
    @GetMapping("/test/lock")
    public JsonData testLock(){
        RLock lock = redissonClient.getLock("lock:coupon:1");
        lock.lock();
        try {
            log.info("加锁成功，处理业务逻辑。。。" + Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(20);
        }catch (Exception e){

        }finally {
            log.info("解锁成功，{}",Thread.currentThread().getId());
            lock.unlock();
        }
        return JsonData.buildSuccess();
    }
*/




}

