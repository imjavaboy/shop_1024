package com.gbq.service;

import com.gbq.model.BannerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gbq.model.vo.BannerVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-13
 */
public interface BannerService extends IService<BannerDO> {


    List<BannerVo> listBanner();

}
