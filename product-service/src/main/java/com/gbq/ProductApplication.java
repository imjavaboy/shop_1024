package com.gbq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 郭本琪
 * @description
 * @date 2022/10/30 22:23
 * @Copyright 总有一天，会见到成功
 */

@SpringBootApplication
@MapperScan("com.gbq.mapper")
@EnableTransactionManagement
public class ProductApplication {

    public static void main(String [] args){
        SpringApplication.run(ProductApplication.class,args);
    }

}
