package sk.qbsw.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.rabbitmq.configuration.config.RoutingConfig;

@Configuration
public class TopicRoutingConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "queue-config.topic-routing.topic-routing-queue-1")
    public RoutingConfig firstTopicRoutingQueueConfig() {
        return new RoutingConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "queue-config.topic-routing.topic-routing-queue-2")
    public RoutingConfig secondTopicRoutingQueueConfig() {
        return new RoutingConfig();
    }

    @Bean
    public Queue firstTopicRoutingQueue(RoutingConfig firstTopicRoutingQueueConfig) {
        return new Queue(firstTopicRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public Queue secondTopicRoutingQueue(RoutingConfig secondTopicRoutingQueueConfig) {
        return new Queue(secondTopicRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic_exchange");
    }

    @Bean
    public Binding firstTopicRouteBinding(TopicExchange topicExchange, Queue firstTopicRoutingQueue,
            RoutingConfig firstTopicRoutingQueueConfig) {
        return BindingBuilder
                .bind(firstTopicRoutingQueue)
                .to(topicExchange)
                .with(firstTopicRoutingQueueConfig.getRoutingPattern());
    }

    @Bean
    public Binding secondTopicRouteBinding(TopicExchange topicExchange, Queue secondTopicRoutingQueue,
            RoutingConfig secondTopicRoutingQueueConfig) {
        return BindingBuilder
                .bind(secondTopicRoutingQueue)
                .to(topicExchange)
                .with(secondTopicRoutingQueueConfig.getRoutingPattern());
    }
}
