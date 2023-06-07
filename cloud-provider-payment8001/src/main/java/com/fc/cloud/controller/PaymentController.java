package com.fc.cloud.controller;

import com.fc.cloud.entity.CommonResult;
import com.fc.cloud.entity.Payment;
import com.fc.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private DiscoveryClient discoveryClient;
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
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
//            eureka上有几个服务
            log.info("Service++++++"+service);
        }
//        某一个名称下的
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId()+"/t"+instance.getHost()+"/t"+instance.getPort()+"/t"+instance.getUri());
        }
        return this.discoveryClient;
    }
    @GetMapping("payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("payment/feign/timeout")
    public String paymentFeignTimeout(){
        //模拟延迟三秒钟
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }

}
