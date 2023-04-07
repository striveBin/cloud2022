package com.fc.cloud.controller;

import com.fc.cloud.entity.CommonResult;
import com.fc.cloud.entity.Payment;
import com.fc.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @PostMapping("payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        System.out.println(result);
        log.info("插入结果"+result);
        if (result > 0){
            return new CommonResult(200,"插入数据库成功,serverPort  "+serverPort,result);
        }else {
            return new CommonResult(-1,"插入失败",null);
        }
    }
    @GetMapping("payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        System.out.println(id);
        Payment result = paymentService.getPaymentById(id);
        log.info("查询结果"+result);
        System.out.println(123);
        if (result != null){
            return new CommonResult(200,"查询成功,serverPort   "+serverPort,result);
        }else {
            return new CommonResult(-1,"查询失败",null);
        }
    }
}
