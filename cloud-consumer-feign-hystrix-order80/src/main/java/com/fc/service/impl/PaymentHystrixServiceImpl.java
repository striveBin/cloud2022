package com.fc.service.impl;

import com.fc.service.PaymentHystrixService;
import org.springframework.stereotype.Service;

@Service
public class PaymentHystrixServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentHystrixServiceImpl.paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentHystrixServiceImpl.paymentInfo_TimeOut";
    }
}
