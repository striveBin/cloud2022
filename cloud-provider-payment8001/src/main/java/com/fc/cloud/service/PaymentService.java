package com.fc.cloud.service;

import com.fc.cloud.entity.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById( Long id);
}
