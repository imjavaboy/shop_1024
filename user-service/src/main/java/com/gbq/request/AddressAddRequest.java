package com.gbq.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/4 19:09
 * @Copyright 总有一天，会见到成功
 */
@Data
@ApiModel("地址传输对象")
public class AddressAddRequest {

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @ApiModelProperty("是否默认收获地址")
    @JsonProperty("default_status")
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    @ApiModelProperty("收货人姓名")
    @JsonProperty("receive_name")
    private String receiveName;

    /**
     * 收货人电话
     */
    @ApiModelProperty("收货人电话")
    private String phone;

    /**
     * 省/直辖市
     */
    @ApiModelProperty("省/直辖市")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty("区")
    private String region;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    @JsonProperty("detail_address")
    private String detailAddress;


}
