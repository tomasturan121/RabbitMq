package com.ferratum.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ferratum.rabbit.api.config.DirectRoutingConfig;


@Configuration
public class DirectRoutingConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "queue-config.routing-with-key.routing-queue-1")
    public DirectRoutingConfig firstRoutingQueueConfig() {
        return new DirectRoutingConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "queue-config.routing-with-key.routing-queue-2")
    public DirectRoutingConfig secondRoutingQueueConfig() {
        return new DirectRoutingConfig();
    }

    @Bean
    public Queue firstRoutingQueue(DirectRoutingConfig firstRoutingQueueConfig) {
        return new Queue(firstRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public Queue secondRoutingQueue(DirectRoutingConfig secondRoutingQueueConfig) {
        return new Queue(secondRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }

    @Bean
    public Binding firstRouteBinding(DirectExchange directExchange, Queue firstRoutingQueue,
            DirectRoutingConfig firstRoutingQueueConfig) {
        return BindingBuilder.bind(firstRoutingQueue).to(directExchange).with(firstRoutingQueueConfig.getRoutingKey());
    }

    @Bean
    public Binding secondRouteBinding(DirectExchange directExchange, Queue secondRoutingQueue,
            DirectRoutingConfig secondRoutingQueueConfig) {
        return BindingBuilder.bind(secondRoutingQueue).to(directExchange)
                .with(secondRoutingQueueConfig.getRoutingKey());
    }
}
