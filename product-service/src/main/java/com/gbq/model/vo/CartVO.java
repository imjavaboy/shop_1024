package com.gbq.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 18:03
 * @Copyright 总有一天，会见到成功
 */
@Data
@Setter
public class CartVO {

    @JsonProperty("cart_items")
    private List<CartItemVO> cartItems;

    @JsonProperty("total_num")
    private Integer totalNum;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("real_pay_price")
    private BigDecimal realPayPrice;

    public List<CartItemVO> getCartItems() {
        return cartItems;
    }

    public Integer getTotalNum() {

        if (this.cartItems != null){
            int sum = cartItems.stream().mapToInt(CartItemVO::getBuyNum).sum();
            return sum;
        }
        return 0;
    }

    public BigDecimal getTotalPrice() {
     BigDecimal amount = new BigDecimal("0");
     if (cartItems != null){
         for (CartItemVO cartItem : cartItems) {
             amount = amount.add(cartItem.getTotalAmount());
         }
     }
     return amount;
    }

    public BigDecimal getRealPayPrice() {
        return realPayPrice;
    }
}
