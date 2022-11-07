package com.gbq.controller;


import com.gbq.enums.BizCodeEnum;
import com.gbq.model.vo.UserVo;
import com.gbq.request.UserLoginRequest;
import com.gbq.request.UserRequestRegister;
import com.gbq.service.FileService;
import com.gbq.service.UserService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 二当家小D
 * @since 2021-01-26
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {


    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @ApiOperation("用户头像上传")
    @PostMapping("/upload")
    public JsonData uploadUserImg(  @ApiParam(value = "文件上传",required = true) @RequestPart("file")MultipartFile file){
        String s = fileService.uploadUserImg(file);
        return s != null ? JsonData.buildSuccess(s)  : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public JsonData register(@ApiParam("用户注册对象") @RequestBody UserRequestRegister userRequestRegister){
        return userService.register(userRequestRegister);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public JsonData login(@ApiParam("用户登录对象") @RequestBody UserLoginRequest userLoginRequest){
        JsonData jsonData = userService.login(userLoginRequest);
        return jsonData;
    }

    @ApiOperation("个人信息查询")
    @GetMapping("/details")
    public JsonData detail(){
        UserVo userVo = userService.findUserDetails();
        return JsonData.buildSuccess(userVo);
    }


}

