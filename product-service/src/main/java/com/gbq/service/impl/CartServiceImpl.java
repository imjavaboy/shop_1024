package com.gbq.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbq.constant.CacheKey;
import com.gbq.enums.BizCodeEnum;
import com.gbq.exception.BizException;
import com.gbq.interceptor.LoginInterceptor;
import com.gbq.mapper.BannerMapper;
import com.gbq.model.BannerDO;
import com.gbq.model.LoginUser;
import com.gbq.model.vo.CartItemVO;
import com.gbq.model.vo.CartVO;
import com.gbq.model.vo.ProductVO;
import com.gbq.request.CartRequest;
import com.gbq.service.BannerService;
import com.gbq.service.CartService;
import com.gbq.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 20:34
 * @Copyright 总有一天，会见到成功
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductService productService;

    @Override
    public void changeItemNum(CartRequest cartRequest) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        Object o = myCartOps.get(cartRequest.getProductId());
        if (o == null){
            throw new BizException(BizCodeEnum.CART_FAIL);
        }
        String obj = (String)o ;

        CartItemVO cartItemVO = JSON.parseObject(obj, CartItemVO.class);
        cartItemVO.setBuyNum(cartRequest.getBuyNum());
        myCartOps.put(cartRequest.getProductId(),JSON.toJSONString(cartItemVO));
    }


    @Override
    public void deleteItem(long productId) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        myCartOps.delete(productId);

    }

    @Override
    public CartVO getMyCart() {



        List<CartItemVO> cartItemVOList = buildCartItem(false);
        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);
        return cartVO;
    }

    private List<CartItemVO> buildCartItem(boolean latestPrice) {

        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        List<Object> itemList = myCartOps.values();
        List<CartItemVO> cartItemVOList = new ArrayList<>();
        List<Long> productIdList = new ArrayList<>();
        for (Object item : itemList) {
            CartItemVO cartItemVO = JSON.parseObject((String) item, CartItemVO.class);
            cartItemVOList.add(cartItemVO);
        }
        if (latestPrice){
            setProductLatestPrice(cartItemVOList,productIdList);
        }
        return cartItemVOList;
    }

    private void setProductLatestPrice(List<CartItemVO> cartItemVOList, List<Long> productIdList) {
        List<ProductVO> productsByIdBatch = productService.findProductsByIdBatch(productIdList);
        Map<Long, ProductVO> collect = productsByIdBatch.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));

        cartItemVOList.stream().forEach(cartItemVO -> {
            ProductVO productVO = collect.get(cartItemVO.getProductId());
            cartItemVO.setProductTitle(productVO.getTitle());
            cartItemVO.setProductImg(productVO.getCoverImg());
            cartItemVO.setAmount(productVO.getAmount());
        });
    }

    @Override
    public void clear() {
        String cartKey = getCartKey();
        redisTemplate.delete(cartKey);
    }

    @Override
    public void addCart(CartRequest cartRequest) {
        long productId = cartRequest.getProductId();
        int buyNum = cartRequest.getBuyNum();

        //获取购物车
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();

        Object cacheObj = myCartOps.get(productId);
        String result = "";
        if (cacheObj != null){
            result = (String)cacheObj;
        }
        if (StringUtils.isBlank(result)){
            //不存在则新建商品项
            CartItemVO cartItemVO = new CartItemVO();
            ProductVO productVO = productService.findDetailById(productId);
            if (productVO == null){
                throw new BizException(BizCodeEnum.CART_FAIL);
            }
            cartItemVO.setAmount(productVO.getAmount());
            cartItemVO.setBuyNum(buyNum);
            cartItemVO.setProductId(productId);
            cartItemVO.setProductImg(productVO.getCoverImg());
            cartItemVO.setProductTitle(productVO.getTitle());

            myCartOps.put(productId, JSON.toJSONString(cartItemVO));

        }else{
            //存在则直接添加商品数量
            CartItemVO cartItemVO = JSON.parseObject(result, CartItemVO.class);
            cartItemVO.setBuyNum(cartItemVO.getBuyNum() + buyNum);
            myCartOps.put(productId,JSON.toJSONString(cartItemVO));
        }

    }


    /**
     *  抽取购物车通用方法
     * @param
     * @since 2022/11/13
     * @return
     */
    private BoundHashOperations<String,Object,Object> getMyCartOps(){
        String cartKey = getCartKey();
        return redisTemplate.boundHashOps(cartKey);
    }

    /**
     *  购物车key的获取方法
     * @param
     * @since 2022/11/13
     * @return
     */
    private String getCartKey(){
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String cartKey = String.format(CacheKey.CART_KEY,loginUser.getId());

        return cartKey;

    }
}
