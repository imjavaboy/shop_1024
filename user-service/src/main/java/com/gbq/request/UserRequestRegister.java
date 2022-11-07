package com.gbq.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/2 14:39
 * @Copyright 总有一天，会见到成功
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户注册对象")
public class UserRequestRegister {

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("密码")
    private String pwd;

    @ApiModelProperty("头像")
    @JsonProperty("head_img")
    private  String headImg;

    @ApiModelProperty("签名")
    private String slogan;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("验证码")
    private String code;

}
