package com.ferratum.rabbitMqProject.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfiguration {

    @Value("${spring.rabbitmq-first-queue}")
    private String firstQueueName;

    @Value("${spring.rabbitmq-second-queue}")
    private String secondQueueName;

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_Exchange", Boolean.TRUE, Boolean.FALSE);
    }

    @Bean
    public Queue firstQueue() {
        return new Queue(firstQueueName, Boolean.TRUE);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(secondQueueName, Boolean.TRUE);
    }

    @Bean
    public Binding firstBinding(FanoutExchange fanoutExchange, Queue firstQueue) {
        return BindingBuilder.bind(firstQueue).to(fanoutExchange);
    }

    @Bean
    public Binding secondBinding(FanoutExchange fanoutExchange, Queue secondQueue) {
        return BindingBuilder.bind(secondQueue).to(fanoutExchange);
    }
}
