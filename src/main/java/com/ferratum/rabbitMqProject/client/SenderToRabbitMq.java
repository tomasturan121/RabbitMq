package com.ferratum.rabbitMqProject.client;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SenderToRabbitMq {

    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SenderToRabbitMq(Queue queue, RabbitTemplate rabbitTemplate) {
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String messageText) {
        log.info("Sending message with text: {}", messageText);
        // properties of message
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        // message creation
        Message message = new Message(messageText.getBytes(), messageProperties);
        // send message
        rabbitTemplate.convertAndSend(queue.getName(), message);
        log.info("Message with text: {} has been sent to RabbitMQ", messageText);
    }
}
