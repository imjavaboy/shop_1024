package com.gbq.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/4 18:33
 * @Copyright 总有一天，会见到成功
 */
@Data
public class UserVo {
    private Long id;

    /**
     * 昵称
     */
    private String name;



    /**
     * 头像
     */
    @JsonProperty("head_img")
    private String headImg;

    /**
     * 用户签名
     */
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    private Integer sex;

    /**
     * 积分
     */
    private Integer points;




}
