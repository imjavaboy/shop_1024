package com.gbq.service;

import com.gbq.model.ProductOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbq.request.ConfirmRequest;
import com.gbq.util.JsonData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-13
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    JsonData confirmOrder(ConfirmRequest orderRequest);
}
