package com.gbq.service;




import org.springframework.web.multipart.MultipartFile;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/1 17:26
 * @Copyright 总有一天，会见到成功
 */

public interface FileService {
    public String uploadUserImg(MultipartFile file);
}
