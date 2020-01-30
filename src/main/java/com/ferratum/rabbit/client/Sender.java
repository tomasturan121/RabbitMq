package com.ferratum.rabbit.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ferratum.rabbit.configuration.HeadersRoutingConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Sender {

    private final FanoutExchange fanoutExchange;
    private final DirectExchange directExchange;
    private final TopicExchange topicExchange;
    private final HeadersExchange headersExchange;

    private final Queue simpleQueue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Sender(
            FanoutExchange fanoutExchange,
            DirectExchange directExchange,
            TopicExchange topicExchange,
            HeadersExchange headersExchange,
            Queue simpleQueue,
            RabbitTemplate rabbitTemplate) {
        this.fanoutExchange = fanoutExchange;
        this.directExchange = directExchange;
        this.topicExchange = topicExchange;
        this.headersExchange = headersExchange;
        this.simpleQueue = simpleQueue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendSimple(String messageText) {
        log.info("Sending message with text: {}", messageText);
        final Message message = new Message(messageText.getBytes(), createProperties());

        // send message to default Exchange - created automatically
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
        log.info("Sending message with routing key: {} and text: {} to Topic Exchange", routingKey, messageText);
        final Message message = new Message(messageText.getBytes(), createProperties());

        // send message to @Autowired direct exchange defined in configuration
        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);
        log.info("Message with routing key: {} and text: {} has been sent to Topic Exchange of RabbitMQ", routingKey,
                messageText);
    }

    public void headerRequest(String headerValue, String messageText) {
        log.info("Sending message with header value: {} and text: {} to Headers Exchange", headerValue, messageText);

        // create message properties
        final MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // set header
        messageProperties.setHeader(HeadersRoutingConfiguration.HEADER_NAME, headerValue);
        // set properties to message
        final Message message = new Message(messageText.getBytes(), messageProperties);
        // send message to @Autowired direct exchange defined in configuration
        rabbitTemplate.convertAndSend(headersExchange.getName(), "ignoredKey", message);
        log.info("Message with header value: {} and text: {} has been sent to Headers Exchange of RabbitMQ",
                headerValue, messageText);
    }

    // message properties creation method
    private static MessageProperties createProperties() {
        final MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        return messageProperties;
    }
}
