package com.gbq.interceptor;


import com.gbq.enums.BizCodeEnum;
import com.gbq.model.LoginUser;
import com.gbq.util.CommonUtil;
import com.gbq.util.JWTUtil;
import com.gbq.util.JsonData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/3 16:26
 * @Copyright 总有一天，会见到成功
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader("token");
        log.info("accessToken,{}",accessToken);
        if (null == accessToken){
            accessToken = request.getParameter("token");
        }

        log.info("accessToken,{}",accessToken);
        if (StringUtils.isNotBlank(accessToken)){

            Claims claims = JWTUtil.checkJWT(accessToken);
            if (null == claims){
                //未登录
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }
            Long userId = Long.valueOf(claims.get("id").toString());
            String headImg = (String)claims.get("head_img");
            String name = (String)claims.get("name");
            String mail = (String)claims.get("mail");

            LoginUser loginUser = LoginUser.builder()
                    .headImg(headImg)
                    .id(userId)
                    .mail(mail)
                    .name(name).build();


            //通过 ThreadLocal 传递登录信息
            threadLocal.set(loginUser);

            return true;
        }
        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
