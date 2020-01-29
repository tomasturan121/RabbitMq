package com.ferratum.rabbitMqProject.client;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

    // listening to queue defined in configuration
    @RabbitListener(queues = "${spring.rabbitmq-queue}")
    public void listen(String inComingMessageText) {
        log.info("Reading message with text: {}", inComingMessageText);
    }
}
