package com.gbq.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/10 23:31
 * @Copyright 总有一天，会见到成功
 */
@Data
@ApiModel
public class NewUserCouponRequest {

    @ApiModelProperty("用户id")
    @JsonProperty("user_id")
    private Long userId;

    /**
     * 昵称
     */
    @ApiModelProperty("用户名称")
    @JsonProperty("name")
    private String name;



}
