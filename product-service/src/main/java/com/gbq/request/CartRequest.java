package com.gbq.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 20:37
 * @Copyright 总有一天，会见到成功
 */
@ApiModel
@Data
public class CartRequest {

    @ApiModelProperty("添加到购物车")
    @JsonProperty("product_id")
    private long productId;

    @ApiModelProperty("购买数量")
    @JsonProperty("buy_num")
    private int buyNum;

}
