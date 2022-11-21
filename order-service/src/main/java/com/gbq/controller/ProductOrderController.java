package com.gbq.controller;


import com.gbq.enums.ClientEnum;
import com.gbq.enums.ProductOrderPayTypeEnum;
import com.gbq.enums.ProductOrderTypeEnum;
import com.gbq.request.ConfirmRequest;
import com.gbq.service.ProductOrderService;
import com.gbq.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 郭本琪
 * @since 2022-11-13
 */
@Api(tags = "订单模块")
@RestController
@RequestMapping("/api/order/v1")
@Slf4j
public class ProductOrderController {
    @Autowired
    private ProductOrderService productOrderService;

    @ApiOperation("提交订单")
    @PostMapping("confirm")
    public void confirmOrder(@RequestBody ConfirmRequest orderRequest, HttpServletResponse response){

       JsonData jsonData =  productOrderService.confirmOrder(orderRequest);
        if (jsonData.getCode() == 0) {
            String clientType = orderRequest.getClientType();
            String payType = orderRequest.getPayType();
            if (payType.equals(ProductOrderPayTypeEnum.ALIPAY.name())){
                log.info("创建订单成功：{},{}",orderRequest.toString());
               if (clientType.equalsIgnoreCase(ClientEnum.H5.name())){
                   writeData(response,jsonData);
               }else if (clientType.equalsIgnoreCase(ClientEnum.APP.name())){
                   // APP TODO
               }

            }else if (payType.equalsIgnoreCase(ProductOrderPayTypeEnum.WECHAT.name())){
                // 微信支付 TODO

            }
        } else{
            log.error("创建订单失败，{}",jsonData.toString());
        }

    }

    private void writeData(HttpServletResponse response, JsonData jsonData) {
        try {
            response.setContentType("text/html;charset=UTF8");
            response.getWriter().write(jsonData.getData().toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
           log.error("写出html异常");
        }
    }
}

