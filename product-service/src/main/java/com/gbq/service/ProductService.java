package com.gbq.service;

import com.gbq.model.ProductDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbq.model.vo.ProductVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-13
 */
public interface ProductService extends IService<ProductDO> {

    Map<String, Object> pageProduct(int page, int size);

    ProductVO findDetailById(long productId);

    List<ProductVO> findProductsByIdBatch(List<Long> productIdList);
}
