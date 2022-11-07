package com.gbq.controller;


import com.gbq.enums.BizCodeEnum;
import com.gbq.model.AddressDO;
import com.gbq.model.vo.AddressVo;
import com.gbq.request.AddressAddRequest;
import com.gbq.service.AddressService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author 二当家小D
 * @since 2021-01-26
 */
@Api(tags = "收货地址模块")
@RestController
@RequestMapping("/api/address/v1/")
public class AddressController {


    @Autowired
    private AddressService addressService;


    @ApiOperation("根据id查找地址详情")
    @GetMapping("/find/{address_id}")
    public JsonData detail(
            @ApiParam(value = "地址id",required = true)
            @PathVariable("address_id") long addressId){

        AddressVo addressDO = addressService.detail(addressId);


        return null == addressDO ?   JsonData.buildResult(BizCodeEnum.ADDRESS_NO_EXITS)  : JsonData.buildSuccess(addressDO) ;
    }

    @ApiOperation("新增收获地址")
    @PostMapping("/add")
    public JsonData add(@ApiParam("地址对象") @RequestBody AddressAddRequest addressAddRequest){
        int rows = addressService.add(addressAddRequest);
        return JsonData.buildSuccess();
    }

    @DeleteMapping("/del/{address_id}")
    @ApiOperation("删除地址")
    public JsonData delAddr(@ApiParam(value = "地址id",required = true) @PathVariable("address_id") int addressId){
        int row = addressService.del(addressId);
        return row == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.ADDRESS_DEL_FAIL);

    }

    @GetMapping("/list")
    @ApiOperation("查询用户全部的收获地址")
    public JsonData findUserAllAddress(){
       List<AddressVo> addressVoList = addressService.listUserAllAddress();
       return JsonData.buildSuccess(addressVoList);
    }
}

