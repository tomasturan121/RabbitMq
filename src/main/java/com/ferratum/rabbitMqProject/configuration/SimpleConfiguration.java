package com.ferratum.rabbitMqProject.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleConfiguration {

    @Bean
    public Queue simpleQueue() {
        return new Queue("simple_queue");
    }
}
