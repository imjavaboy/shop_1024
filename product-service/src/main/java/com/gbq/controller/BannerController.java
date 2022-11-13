package com.gbq.controller;


import com.gbq.model.vo.BannerVo;
import com.gbq.service.BannerService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-13
 */
@Api(tags = "轮播图模块")
@RestController
@RequestMapping("/api/banner/v1")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation("轮播图列表")
    @GetMapping("/list")
    public JsonData list(){
        List<BannerVo> bannerVos = bannerService.listBanner();
        return JsonData.buildSuccess(bannerVos);
    }
}

