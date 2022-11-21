package com.gbq.service.impl;

import com.gbq.model.ProductOrderDO;
import com.gbq.mapper.ProductOrderMapper;
import com.gbq.request.ConfirmRequest;
import com.gbq.service.ProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbq.util.JsonData;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-13
 */
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements ProductOrderService {



    /**
     *  * 防重提交
     * * 用户微服务-确认收货地址
     * * 商品微服务-获取最新购物项和价格
     * * 订单验价
     *   * 优惠券微服务-获取优惠券
     *   * 验证价格
     * * 锁定优惠券
     * * 锁定商品库存
     * * 创建订单对象
     * * 创建子订单对象
     * * 发送延迟消息-用于自动关单
     * * 创建支付信息-对接三方支付
     * @param
     * @since 2022/11/17
     * @return
     */
    @Override
    public JsonData confirmOrder(ConfirmRequest orderRequest) {
        return null;
    }
}
