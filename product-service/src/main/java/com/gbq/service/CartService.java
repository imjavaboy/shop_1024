package com.gbq.service;


import com.gbq.model.vo.CartVO;
import com.gbq.request.CartRequest;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 20:34
 * @Copyright 总有一天，会见到成功
 */
public interface CartService {

    void addCart(CartRequest cartRequest);

    void clear();

    CartVO getMyCart();

    void deleteItem(long productId);

    void changeItemNum(CartRequest cartRequest);
}
