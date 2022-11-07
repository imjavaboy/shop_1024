package com.gbq.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gbq.enums.AddressStatus;
import com.gbq.interceptor.LoginInterceptor;
import com.gbq.mapper.AddressMapper;
import com.gbq.model.AddressDO;
import com.gbq.model.LoginUser;
import com.gbq.model.vo.AddressVo;
import com.gbq.request.AddressAddRequest;
import com.gbq.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public  AddressVo detail(Long id) {
        //防止越权攻击
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id",id).eq("user_id",loginUser.getId()));

        if (addressDO == null){
            return null;
        }
        AddressVo addressVo = new AddressVo();
        BeanUtils.copyProperties(addressDO,addressVo);
        return addressVo;
    }

    /**
     *  新增收获地址
     * @param
     * @since 2022/11/4
     * @return
     */
    @Override
    public int add(AddressAddRequest addressAddRequest) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        addressDO.setUserId(loginUser.getId());

        BeanUtils.copyProperties(addressAddRequest,addressDO);
        //判断新增收获地址
        if (addressDO.getDefaultStatus() == 1){
            AddressDO defaultAddressDo = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()).eq("default_status", AddressStatus.DEFAULT_STATUS.getStatus()));
            if (defaultAddressDo != null){
                defaultAddressDo.setDefaultStatus(AddressStatus.COMMON_STATUS.getStatus());
                addressMapper.update(defaultAddressDo,new QueryWrapper<AddressDO>().eq("id",defaultAddressDo.getId()));
            }
        }

        int rows = addressMapper.insert(addressDO);
        log.info("新增收获地址，rows={},data={}",rows,addressDO);
        return rows;
    }

    /**
     *  删除地址
     * @param
     * @since 2022/11/4
     * @return
     */
    @Override
    public int del(int addressId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        int rows = addressMapper.delete(new QueryWrapper<AddressDO>().eq("id", addressId).eq("user_id",loginUser.getId()));
        return rows;
    }

    @Override
    public List<AddressVo> listUserAllAddress() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        List<AddressDO> addressDOList = addressMapper.selectList(new QueryWrapper<AddressDO>().eq("user_id", loginUser.getId()));
        List<AddressVo> addressVoList = addressDOList.stream().map(item -> {
            AddressVo addressVo = new AddressVo();
            BeanUtils.copyProperties(item, addressVo);
            return addressVo;
        }).collect(Collectors.toList());
        return addressVoList;
    }
}
