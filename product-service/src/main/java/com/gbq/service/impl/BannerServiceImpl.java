package com.gbq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gbq.model.BannerDO;
import com.gbq.mapper.BannerMapper;
import com.gbq.model.vo.BannerVo;
import com.gbq.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerDO> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerVo> listBanner() {
        List<BannerDO> bannerDOList = bannerMapper.selectList(new QueryWrapper<BannerDO>().orderByAsc("weight"));
        List<BannerVo> bannerVoList = bannerDOList.stream().map(obj -> {
            BannerVo bannerVo = new BannerVo();
            BeanUtils.copyProperties(obj, bannerVo);
            return bannerVo;
        }).collect(Collectors.toList());
        return bannerVoList;
    }
}
