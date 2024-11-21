package com.meta.order.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;


@Service
@RequiredArgsConstructor

public class OrderProducer {
   private final KafkaTemplate<String,OrderConfirmation> kafkaTemplate;
   private final Logger LOGGER=  LoggerFactory.getLogger(OrderProducer.class);

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation){
        LOGGER.info("Sending order confirmation");
        Message<OrderConfirmation> message= MessageBuilder
                .withPayload(orderConfirmation)
                .setHeader(KafkaHeaders.TOPIC,"order-topic")
                .build();
       kafkaTemplate.send(message);
    }
}
