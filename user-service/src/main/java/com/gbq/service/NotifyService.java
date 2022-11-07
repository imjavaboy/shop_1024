package com.gbq.service;


import com.gbq.enums.SendCodeEnum;
import com.gbq.util.JsonData;

public interface NotifyService {

    JsonData sendCode(SendCodeEnum sendCodeEnum, String to );

    /**
     *  判断验证码
     * @param
     * @since 2022/11/2
     * @return
     */
    boolean checkCode(SendCodeEnum userRegister, String mail, String code);
}
