package com.ferratum.rabbitMqProject.client;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReceiverFromRabbitMq {

    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReceiverFromRabbitMq(Queue queue, RabbitTemplate rabbitTemplate) {
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String listen() {
        log.info("Reading message from queue: {}", queue.getName());
        Message returnedMessage = rabbitTemplate.receive(queue.getName());
        return (returnedMessage == null) ? "No message returned!" : new String(returnedMessage.getBody());
    }
}
