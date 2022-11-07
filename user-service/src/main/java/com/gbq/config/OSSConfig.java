package com.gbq.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/1 16:12
 * @Copyright 总有一天，会见到成功
 */
@ConfigurationProperties("aliyun.oss")
@Configuration
@Data
public class OSSConfig {
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketname;
}
