package com.gbq;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 郭本琪
 * @description
 * @date 2022/10/30 22:23
 * @Copyright 总有一天，会见到成功
 */

@SpringBootApplication
@MapperScan("com.gbq.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class UserApplication {

    public static void main(String [] args){
        SpringApplication.run(UserApplication.class,args);
    }

}
