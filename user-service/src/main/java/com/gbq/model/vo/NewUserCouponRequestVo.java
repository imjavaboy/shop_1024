package com.gbq.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/18 15:25
 * @Copyright 总有一天，会见到成功
 */
@Data
public class NewUserCouponRequestVo {

    @JsonProperty("user_id")
    private Long userId;

    /**
     * 昵称
     */
    @JsonProperty("name")
    private String name;

}
