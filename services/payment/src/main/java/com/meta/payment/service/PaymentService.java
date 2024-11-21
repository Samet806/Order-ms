package com.meta.payment.service;

import com.meta.payment.dto.PaymentRequest;
import com.meta.payment.mapper.PaymentMapper;
import com.meta.payment.notification.NotificationProducer;
import com.meta.payment.notification.PaymentNotificationRequest;
import com.meta.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentRepository paymentRepository;
  private final PaymentMapper mapper;
  private NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest paymentRequest) {

        var payment = paymentRepository.save(mapper.toPayment(paymentRequest));

        notificationProducer.sendNotification(new PaymentNotificationRequest(
                paymentRequest.orderReference(),
                paymentRequest.amount(),
                paymentRequest.paymentMethod(),
                paymentRequest.customer().firstname(),
                paymentRequest.customer().lastname(),
                paymentRequest.customer().email()
        ));

        return payment.getId();
    }
}
