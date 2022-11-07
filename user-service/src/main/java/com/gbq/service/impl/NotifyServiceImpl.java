package com.gbq.service.impl;


import com.gbq.component.MailService;
import com.gbq.constant.CacheKey;
import com.gbq.enums.BizCodeEnum;
import com.gbq.enums.SendCodeEnum;
import com.gbq.service.NotifyService;
import com.gbq.util.CheckUtil;
import com.gbq.util.CommonUtil;
import com.gbq.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
public class NotifyServiceImpl implements NotifyService {


    @Autowired
    private MailService mailService;

    /**
     * 验证码的标题
     */
    private static final String SUBJECT= "xxxx验证码";

    /**
     * 验证码的内容
     */
    private static final String CONTENT= "您的验证码是%s,有效时间是60秒,打死也不要告诉任何人";

    /**
     * 5分组有效
     * */
    private static final int CODE_EXPIRE = 60*1000*5;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /** 1. 判断重复发送
     *  发送验证码，存储缓存、
     *  2. 存储发送记录
     * @param
     * @since 2022/10/30
     * @return
     */
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {

        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),to);

        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        System.out.println("呵呵呵呵："+cacheKey);
        System.out.println("呵呵呵呵："+cacheValue);
        //如果不为空，判断60s是否重复发送
        if (StringUtils.isNotBlank(cacheValue)){
            //TODO
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            if (CommonUtil.getCurrentTimestamp() - ttl < 1000*60){
                log.info("重复发送验证码，时间间隔：{}秒",(CommonUtil.getCurrentTimestamp()-ttl)/1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }
        String code = CommonUtil.getRandomCode(6);
        String value = code+"_"+CommonUtil.getCurrentTimestamp();
        redisTemplate.opsForValue().set(cacheKey,value,CODE_EXPIRE, TimeUnit.MILLISECONDS);

        if(CheckUtil.isEmail(to)){
            //邮箱验证码

            mailService.sendMail(to,SUBJECT,String.format(CONTENT,code));
            return JsonData.buildSuccess();

        } else if(CheckUtil.isPhone(to)){
            //短信验证码

        }

        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String mail, String code) {

        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY,sendCodeEnum.name(),mail);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);


        if (StringUtils.isNotBlank(cacheKey)){
            String cacheCode = cacheValue.split("_")[0];
            log.info("cacheCode,{}",cacheCode);
            log.info("code,{}",code);
            if (cacheCode.equals(code)){

                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
