package com.meta.notification.kafka;

import com.meta.notification.email.EmailService;
import com.meta.notification.kafka.order.OrderConfirmation;
import com.meta.notification.kafka.payment.PaymentConfirmation;
import com.meta.notification.model.Notification;
import com.meta.notification.model.NotificationType;
import com.meta.notification.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private  final NotificationRepository notificationRepository;
    private  final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
       log.info(String.format("Consuming the message from payment topic Topic:: %s ",paymentConfirmation));
       notificationRepository.save(
               Notification.builder()
                       .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                       .notificationDate(LocalDateTime.now())
                       .paymentConfirmation(paymentConfirmation)
                       .build()
       );

       //todo send email
        var customerName=paymentConfirmation.customerFirstname()+" "+paymentConfirmation.customerLastname()+" "+ paymentConfirmation.customerEmail();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }



    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from order topic Topic:: %s ",orderConfirmation));
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        //todo send email
        var customerName=orderConfirmation.customer().firstname()+" "+orderConfirmation.customer().lastname();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

    }
}
