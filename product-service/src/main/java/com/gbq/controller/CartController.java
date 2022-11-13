package com.gbq.controller;


import com.gbq.model.vo.CartVO;
import com.gbq.request.CartRequest;
import com.gbq.service.CartService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 20:32
 * @Copyright 总有一天，会见到成功
 */

@RestController
@Api(tags = "购物车模块")
@RequestMapping("/api/cart/v1")
public class CartController {
    @Autowired
    private CartService cartService;


    @ApiOperation("将物品添加到购物车")
    @PostMapping("/add")
    public JsonData addToCart(@RequestBody @ApiParam("购物项") CartRequest cartRequest){

        cartService.addCart(cartRequest);
        return JsonData.buildSuccess();
    }
    @ApiOperation("清空购物车")
    @DeleteMapping("/clear")
    public JsonData clearMyCart(){
        cartService.clear();
        return JsonData.buildSuccess();
    }

    @ApiOperation("查看我的购物车")
    @GetMapping("/mycart")
    public JsonData findMyCart(){
        CartVO cartVO = cartService.getMyCart();
        return JsonData.buildSuccess(cartVO);
    }


    @ApiOperation("删除购物项")
    @DeleteMapping("/delete/{product_id}")
    public JsonData deleteItem(@ApiParam("商品id") @PathVariable("product_id") long productId){
        cartService.deleteItem(productId);
        return JsonData.buildSuccess();
    }

    @ApiOperation("修改购物车数量")
    @PostMapping("/change")
    public JsonData changeItemNum(@RequestBody @ApiParam("购物项") CartRequest cartRequest){

        cartService.changeItemNum(cartRequest);
        return JsonData.buildSuccess();
    }

}
