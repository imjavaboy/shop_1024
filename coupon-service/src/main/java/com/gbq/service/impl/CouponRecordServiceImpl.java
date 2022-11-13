package com.gbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gbq.interceptor.LoginInterceptor;
import com.gbq.model.CouponRecordDO;
import com.gbq.mapper.CouponRecordMapper;
import com.gbq.model.LoginUser;
import com.gbq.service.CouponRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gbq.vo.CouponRecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-05
 */
@Service
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecordDO> implements CouponRecordService {

    @Autowired
    private CouponRecordMapper couponRecordMapper;

    @Override
    public Map<String, Object> pageCouponRecord(int page, int size) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        Page<CouponRecordDO> objectPage = new Page<>(page, size);
        Page<CouponRecordDO> recordDOPage = couponRecordMapper.selectPage(objectPage, new QueryWrapper<CouponRecordDO>()
                .eq("user_id", loginUser.getId()).orderByDesc("create_time"));
        Map<String,Object> map = new HashMap<>();
        map.put("total_record",recordDOPage.getTotal());
        map.put("total_page",recordDOPage.getPages());
        map.put("current_data",recordDOPage.getRecords().stream().map(obj->beanProcess(obj)).collect(Collectors.toList()));
        return map;
    }

    private CouponRecordVo beanProcess(CouponRecordDO obj) {
        CouponRecordVo couponRecordVo = new CouponRecordVo();
        BeanUtils.copyProperties(obj,couponRecordVo);
        return couponRecordVo;
    }

    @Override
    public CouponRecordVo findById(long recordId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        CouponRecordDO couponRecordDO = couponRecordMapper.selectOne(new QueryWrapper<CouponRecordDO>()
                .eq("id",recordId)
                .eq("user_id",loginUser.getId()));
        if (couponRecordDO == null){
            return null;
        }
      return beanProcess(couponRecordDO);
    }
}
