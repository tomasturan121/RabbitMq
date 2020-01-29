package com.ferratum.rabbitMqProject.client;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Sender {

    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Sender(Queue queue, RabbitTemplate rabbitTemplate) {
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String messageText) {
        log.info("Sending message with text: {}", messageText);
        // properties of message
        final MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // message creation
        final Message message = new Message(messageText.getBytes(), messageProperties);
        // send message to queue defined in configuration
        rabbitTemplate.convertAndSend(queue.getName(), message);
        log.info("Message with text: {} has been sent to RabbitMQ", messageText);
    }
}
