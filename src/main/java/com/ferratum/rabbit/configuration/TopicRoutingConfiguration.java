package com.ferratum.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ferratum.rabbit.api.config.TopicRoutingConfig;

@Configuration
public class TopicRoutingConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "queue-config.topic-routing.topic-routing-queue-1")
    public TopicRoutingConfig firstTopicRoutingQueueConfig() {
        return new TopicRoutingConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "queue-config.topic-routing.topic-routing-queue-2")
    public TopicRoutingConfig secondTopicRoutingQueueConfig() {
        return new TopicRoutingConfig();
    }

    @Bean
    public Queue firstTopicRoutingQueue(TopicRoutingConfig firstTopicRoutingQueueConfig) {
        return new Queue(firstTopicRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public Queue secondTopicRoutingQueue(TopicRoutingConfig secondTopicRoutingQueueConfig) {
        return new Queue(secondTopicRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic_exchange");
    }

    @Bean
    public Binding firstTopicRouteBinding(TopicExchange topicExchange, Queue firstTopicRoutingQueue,
            TopicRoutingConfig firstTopicRoutingQueueConfig) {
        return BindingBuilder.bind(firstTopicRoutingQueue).to(topicExchange)
                .with(firstTopicRoutingQueueConfig.getRoutingPattern());
    }

    @Bean
    public Binding secondTopicRouteBinding(TopicExchange topicExchange, Queue secondTopicRoutingQueue,
            TopicRoutingConfig secondTopicRoutingQueueConfig) {
        return BindingBuilder.bind(secondTopicRoutingQueue).to(topicExchange)
                .with(secondTopicRoutingQueueConfig.getRoutingPattern());
    }

}
