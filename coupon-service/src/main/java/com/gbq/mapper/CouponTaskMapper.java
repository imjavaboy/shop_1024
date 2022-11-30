package com.gbq.mapper;

import com.gbq.model.CouponTaskDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-21
 */
@Repository
public interface CouponTaskMapper extends BaseMapper<CouponTaskDO> {

    int insertBatch(List<CouponTaskDO> couponTaskDOList);
}
