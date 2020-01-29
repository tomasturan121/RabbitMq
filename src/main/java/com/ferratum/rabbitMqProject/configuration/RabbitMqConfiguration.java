package com.ferratum.rabbitMqProject.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfiguration {

    @Value("${spring.rabbitmq-queue}")
    private String queueName;

    @Bean
    public Queue queue() {
        return new Queue(queueName, Boolean.TRUE);
    }
}
