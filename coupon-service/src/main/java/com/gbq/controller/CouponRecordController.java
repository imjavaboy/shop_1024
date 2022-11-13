package com.gbq.controller;


import com.gbq.enums.BizCodeEnum;
import com.gbq.service.CouponRecordService;
import com.gbq.util.JsonData;
import com.gbq.vo.CouponRecordVo;
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
@Api(tags = "优惠券领取模块")
@RestController
@RequestMapping("/api/coupon_record/v1")
public class CouponRecordController {

    @Autowired
    private CouponRecordService couponRecordService;

    @ApiOperation("领券记录查询")
    @GetMapping("/page")
    public JsonData page(@RequestParam(value = "page",defaultValue = "1") int page,
                          @RequestParam(value = "size",defaultValue = "20")int size){
        Map<String,Object> res = couponRecordService.pageCouponRecord(page,size);
        return JsonData.buildSuccess(res);
    }

    @ApiOperation("查询优惠券记录详情")
    @GetMapping("detail/{record_id}")
    public JsonData getCouponRecordDetail(@ApiParam("记录id") @PathVariable("record_id") long recordId){
        CouponRecordVo couponRecordVo = couponRecordService.findById(recordId);
        return couponRecordVo == null ? JsonData.buildSuccess(BizCodeEnum.COUPON_NO_EXITS) : JsonData.buildSuccess(couponRecordVo);

    }
}

