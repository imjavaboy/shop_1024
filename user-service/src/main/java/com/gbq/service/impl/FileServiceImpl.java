package com.gbq.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.gbq.config.OSSConfig;
import com.gbq.service.FileService;
import com.gbq.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/1 17:37
 * @Copyright 总有一天，会见到成功
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSConfig ossConfig;


    @Override
    public String uploadUserImg(MultipartFile file) {

        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        String endpoint = ossConfig.getEndpoint();
        String bucketname = ossConfig.getBucketname();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String originalFilename = file.getOriginalFilename();

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String floder = dateTimeFormatter.format(ldt);

        //拼装路径
        String filename = CommonUtil.generateUUID();

        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));

        String newFileName = "usr/"+floder+"/"+filename + extensionName;

        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketname, newFileName, file.getInputStream());
            if (putObjectResult!=null){
                String imgUrl = "https://"+bucketname+"."+endpoint+"/"+newFileName;
                return imgUrl;
            }
        } catch (IOException e) {
            log.error("文件上传失败");
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
}
