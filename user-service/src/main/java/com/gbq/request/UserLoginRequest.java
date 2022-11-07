package com.gbq.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/3 10:17
 * @Copyright 总有一天，会见到成功
 */
@Data
@ApiModel("登录对象")
public class UserLoginRequest {

    @ApiModelProperty("邮箱")
    private String mail;
    @ApiModelProperty("密码")
    private String pwd;
}
