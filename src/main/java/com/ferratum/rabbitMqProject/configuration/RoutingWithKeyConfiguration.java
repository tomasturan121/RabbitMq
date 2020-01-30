package com.ferratum.rabbitMqProject.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RoutingWithKeyConfiguration {

    @Value("${spring.config.routing-with-key.routing-queue-1.name}")
    private String firstRoutingQueueName;

    @Value("${spring.config.routing-with-key.routing-queue-1.routing-key}")
    private String firstRoutingQueueRoutingKey;

    @Value("${spring.config.routing-with-key.routing-queue-2.name}")
    private String secondRoutingQueueName;

    @Value("${spring.config.routing-with-key.routing-queue-2.routing-key}")
    private String secondRoutingQueueRoutingKey;

    @Bean
    public Queue firstRoutingQueue() {
        return new Queue(firstRoutingQueueName, Boolean.TRUE);
    }

    @Bean
    public Queue secondRoutingQueue() {
        return new Queue(secondRoutingQueueName, Boolean.TRUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }

    @Bean
    public Binding firstRouteBinding(DirectExchange directExchange, Queue firstRoutingQueue) {
        return BindingBuilder.bind(firstRoutingQueue).to(directExchange).with(firstRoutingQueueRoutingKey);
    }

    @Bean
    public Binding secondRouteBinding(DirectExchange directExchange, Queue secondRoutingQueue) {
        return BindingBuilder.bind(secondRoutingQueue).to(directExchange).with(secondRoutingQueueRoutingKey);
    }
}
