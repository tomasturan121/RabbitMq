package com.ferratum.rabbitMqProject.client;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

    @RabbitListener(queues = "#{firstQueue.name}")
    public void listenToFirstQueue(String in) {
        log.info("Reading message with text: {} from first queue", in);
    }

    @RabbitListener(queues = "#{secondQueue.name}")
    public void listenToSecondQueue(String in) {
        log.info("Reading message with text: {} from second queue", in);
    }
}
