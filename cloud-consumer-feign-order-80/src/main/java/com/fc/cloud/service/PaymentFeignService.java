package com.fc.cloud.service;

import com.fc.cloud.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
@Service
public interface PaymentFeignService {
    @GetMapping("payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id);
}
