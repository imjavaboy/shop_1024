package com.gbq;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/13 23:43
 * @Copyright 总有一天，会见到成功
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.gbq.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class OrderApplication {
    public static void main(String [] args){
        SpringApplication.run(OrderApplication.class,args);
    }
}
