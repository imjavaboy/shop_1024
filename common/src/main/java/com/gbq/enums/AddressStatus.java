package com.gbq.enums;


import lombok.Getter;

/**
 * @author 郭本琪
 * @description
 * @date 2022/11/4 19:28
 * @Copyright 总有一天，会见到成功
 */
public enum AddressStatus {


    DEFAULT_STATUS(1),
    COMMON_STATUS(0);

    @Getter
    private int status;
    private AddressStatus(int status){
        this.status = status;
    }
}
