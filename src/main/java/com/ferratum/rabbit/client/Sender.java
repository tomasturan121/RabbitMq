package com.ferratum.rabbit.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Sender {

    private final FanoutExchange fanoutExchange;
    private final DirectExchange directExchange;
    private final TopicExchange topicExchange;

    private final Queue simpleQueue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Sender(
            FanoutExchange fanoutExchange,
            DirectExchange directExchange,
            TopicExchange topicExchange,
            Queue simpleQueue,
            RabbitTemplate rabbitTemplate) {
        this.fanoutExchange = fanoutExchange;
        this.directExchange = directExchange;
        this.topicExchange = topicExchange;
        this.simpleQueue = simpleQueue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendSimple(String messageText) {
        log.info("Sending message with text: {}", messageText);
        final Message message = new Message(messageText.getBytes(), createProperties());

        // send message to default Exchange - created automatically with same name as defined Queue
        rabbitTemplate.convertAndSend(simpleQueue.getName(), message);
        log.info("Message with text: {} has been send to Default Exchange of RabbitMQ", messageText);
    }

    public void publish(String messageText) {
        log.info("Publishing message with text: {}", messageText);
        final Message message = new Message(messageText.getBytes(), createProperties());

        // send message to @Autowired fanout exchange defined in configuration
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "ignoredRoutingKey", message);
        log.info("Message with text: {} has been published to Fanout Exchange of RabbitMQ", messageText);
    }

    public void route(String routingKey, String messageText) {
        log.info("Sending message with routing key: {} and text: {}", routingKey, messageText);
        final Message message = new Message(messageText.getBytes(), createProperties());

        // send message to @Autowired direct exchange defined in configuration
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, message);
        log.info("Message with routing key: {} and text: {} has been sent to Direct Exchange RabbitMQ", routingKey,
                messageText);
    }

    public void topicRoute(String routingKey, String messageText) {
        log.info("Sending message with routing key: {} and text: {} to Topic exchange", routingKey, messageText);
        final Message message = new Message(messageText.getBytes(), createProperties());

        // send message to @Autowired direct exchange defined in configuration
        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);
        log.info("Message with routing key: {} and text: {} has been sent to Topic Exchange of RabbitMQ", routingKey,
                messageText);
    }

    // message properties creation method
    private static MessageProperties createProperties() {
        final MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        return messageProperties;
    }
}
