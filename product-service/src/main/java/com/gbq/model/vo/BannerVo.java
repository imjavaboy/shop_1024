package com.gbq.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 13:45
 * @Copyright 总有一天，会见到成功
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BannerVo {

    private Integer id;

    /**
     * 图片
     */
    private String img;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 权重
     */
    private Integer weight;

}
