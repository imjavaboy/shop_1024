package com.gbq.util;


import com.gbq.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/3 15:07
 * @Copyright 总有一天，会见到成功
 */
@Slf4j
@Component
public class JWTUtil {


    /**
     * token 过期时间，正常是7天，方便测试我们改为70
     */
    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 7 * 10;

    /**
     * 加密的秘钥
     */
    private static final String SECRET = "gbq666";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "gbq666";

    /**
     * subject
     */
    private static final String SUBJECT = "gbq";


    /**
     * 根据用户信息，生成令牌
     *
     * @param loginUser
     * @return
     */
    public static String geneJsonWebToken(LoginUser loginUser) {

        if (loginUser == null) {
            throw new NullPointerException("loginUser对象为空");
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                //payload
                .claim("head_img", loginUser.getHeadImg())
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .claim("mail", loginUser.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        return token;
    }


    /**
     * 校验token的方法
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try {

            final Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

            return claims;

        } catch (Exception e) {
            log.info("jwt token解密失败");
            return null;
        }

    }

}
