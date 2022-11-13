package com.gbq.controller;


import com.gbq.model.vo.ProductVO;
import com.gbq.service.ProductService;
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
 * @since 2022-11-13
 */
@Api(tags = "商品模块")
@RestController
@RequestMapping("/api/product/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("分页查询商品")
    @GetMapping("/page_product")
    public JsonData pageCouponList(@ApiParam("当前页") @RequestParam(value = "page",defaultValue = "1") int page,
                                   @ApiParam("每页显示多少条") @RequestParam(value = "size",defaultValue = "20") int size){
        Map<String, Object> pageProduct = productService.pageProduct(page, size);

        return JsonData.buildSuccess(pageProduct);
    }

    @ApiOperation("商品详情")
    @GetMapping("/detail/{product_id}")
    public JsonData detail(@ApiParam(value = "商品id",required = true) @PathVariable("product_id") long productId){
       ProductVO productVO = productService.findDetailById(productId);
       return JsonData.buildSuccess(productVO);
    }

}

