package com.gbq.service;


import com.gbq.model.vo.UserVo;
import com.gbq.request.UserLoginRequest;
import com.gbq.request.UserRequestRegister;
import com.gbq.util.JsonData;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/2 14:48
 * @Copyright 总有一天，会见到成功
 */
public interface UserService {

    JsonData register(UserRequestRegister requestRegister);

    JsonData login(UserLoginRequest userLoginRequest);

    UserVo findUserDetails();
}
