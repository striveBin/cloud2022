package com.fc.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_OK,id="+id;
    }
    //指定方法出问题兜底方法(系统报错、超时)
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
//            此线程超时时间为3s
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_Timeout(Integer id){
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_Timeout,id="+id;
    }
    public String paymentInfo_TimeoutHandler(Integer id){
        log.info("线程池："+Thread.currentThread().getName()+"paymentInfo_TimeoutHandler,id="+id);
        return "线程池："+Thread.currentThread().getName()+"paymentInfo_TimeoutHandler,id="+id;
    }
}
