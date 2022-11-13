package com.gbq.service.impl;

import com.gbq.model.ProductDO;
import com.gbq.mapper.ProductMapper;
import com.gbq.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

}
