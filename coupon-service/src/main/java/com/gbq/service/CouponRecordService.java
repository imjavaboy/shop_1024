package com.gbq.service;

import com.gbq.model.CouponRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbq.request.LockCouponRecordRequest;
import com.gbq.util.JsonData;
import com.gbq.vo.CouponRecordVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-05
 */
public interface CouponRecordService extends IService<CouponRecordDO> {

    Map<String, Object> pageCouponRecord(int page, int size);

    CouponRecordVo findById(long recordId);

    JsonData lockCouponRecords(LockCouponRecordRequest recordRequest);
}
