package com.gbq.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 郭本琪
 * @description 购物项
 * @date 2022/11/13 18:06
 * @Copyright 总有一天，会见到成功
 */
@Data
@Setter
public class CartItemVO {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("buy_num")
    private Integer buyNum;

    @JsonProperty("product_title")
    private String productTitle;

    @JsonProperty("product_img")
    private String productImg;

    private BigDecimal amount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalAmount() {
       return this.amount.multiply(new BigDecimal(this.buyNum));
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
