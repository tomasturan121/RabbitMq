package com.ferratum.rabbit.client;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Receiver {

    private final Queue simpleQueue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Receiver(Queue simpleQueue, RabbitTemplate rabbitTemplate) {
        this.simpleQueue = simpleQueue;
        this.rabbitTemplate = rabbitTemplate;
    }

    // simple receive using RabbitTemplate
    public String simpleListen() {
        final Message message = rabbitTemplate.receive(simpleQueue.getName());
        log.info("Message read from simple queue: {}", message);
        return (message == null) ? "No message text found!" : new String(message.getBody());
    }

    // publish/subscribe listeners
    @RabbitListener(queues = "#{firstSubscriberQueue.name}")
    public void listenToFirstSubscriberQueue(String in) {
        log.info("Reading message with text: {} from first subscriber queue", in);
    }

    @RabbitListener(queues = "#{secondSubscriberQueue.name}")
    public void listenToSecondSubscriberQueue(String in) {
        log.info("Reading message with text: {} from second subscriber queue", in);
    }

    // routing with routing-key listeners
    @RabbitListener(queues = "#{firstRoutingQueue.name}")
    public void listenToFirstRouteQueue(String in) {
        log.info("Reading message with text: {} from first route queue", in);
    }

    @RabbitListener(queues = "#{secondRoutingQueue.name}")
    public void listenToSecondRouteQueue(String in) {
        log.info("Reading message with text: {} from second route queue", in);
    }

    // topic routing with routing-key listeners
    @RabbitListener(queues = "#{firstTopicRoutingQueue.name}")
    public void listenToFirstTopicRouteQueue(String in) {
        log.info("Reading message with text: {} from first topic routing queue", in);
    }

    @RabbitListener(queues = "#{secondTopicRoutingQueue.name}")
    public void listenToSecondTopicRouteQueue(String in) {
        log.info("Reading message with text: {} from second topic routing queue", in);
    }
}
