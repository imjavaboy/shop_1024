package com.gbq.config;


import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/7 19:33
 * @Copyright 总有一天，会见到成功
 */

@Configuration
@Data
public class AppConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;



    /**配置分布式锁*/
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();

        //单机版配置
        SingleServerConfig serverConfig = config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);

        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    /**
     * 配置redis的乱码
     * */
    @Bean
    public RedisTemplate<String ,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String ,Object> redisTemplate =  new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;

    }
}
