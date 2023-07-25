package com.fc.controller;

import com.fc.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
//默认fallback
@DefaultProperties(defaultFallback = "payment_Global_FallBackMethods")
public class OrderHystrixController {
    @Autowired
    private PaymentHystrixService paymentHystrixService;
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }
    @GetMapping("/consumer/payment/hystrix/timeOut/{id}")
    //指定方法出问题兜底方法(系统报错、超时)
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
////            此线程超时时间为3s
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand
    public String paymentInfo_Timeout(Integer id){
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
    public String paymentInfo_TimeoutHandler(Integer id){
        log.info("线程池："+Thread.currentThread().getName()+"paymentInfo_TimeoutHandler,id="+id);
        return "消费者80，服务忙请稍后";
    }
    //全局fallback
    public String payment_Global_FallBackMethods(){
        return "Global异常处理";
    }
}
