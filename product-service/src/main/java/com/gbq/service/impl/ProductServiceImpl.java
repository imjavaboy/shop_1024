package com.gbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gbq.model.ProductDO;
import com.gbq.mapper.ProductMapper;
import com.gbq.model.vo.ProductVO;
import com.gbq.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Map<String, Object> pageProduct(int page, int size) {
        Page<ProductDO> pageOfProduct = new Page<>(page, size);
        Page<ProductDO> productDOPage = productMapper.selectPage(pageOfProduct, null);
        Map<String, Object> map = new HashMap<>(3);
        map.put("total_record", productDOPage.getTotal());
        map.put("total_page", productDOPage.getPages());
        map.put("data", productDOPage.getRecords().stream().map(obj -> beanProcess(obj)).collect(Collectors.toList()));


        return map;
    }

    private ProductVO beanProcess(ProductDO obj) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(obj, productVO);
        productVO.setStock(obj.getStock() - obj.getLockStock());
        return productVO;
    }

    @Override
    public ProductVO findDetailById(long productId) {
        ProductDO productDO = productMapper.selectById(productId);

        return beanProcess(productDO);


    }

    @Override
    public List<ProductVO> findProductsByIdBatch(List<Long> productIdList) {
        List<ProductDO> productDOList = productMapper.selectList(new QueryWrapper<ProductDO>().in("id", productIdList));
        List<ProductVO> collect = productDOList.stream().map(obj -> beanProcess(obj)).collect(Collectors.toList());
        return collect;
    }
}
