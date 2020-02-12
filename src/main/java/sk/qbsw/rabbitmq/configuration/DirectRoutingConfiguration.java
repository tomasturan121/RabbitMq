package sk.qbsw.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.rabbitmq.configuration.config.RoutingConfig;


@Configuration
public class DirectRoutingConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "queue-config.routing-with-key.routing-queue-1")
    public RoutingConfig firstRoutingQueueConfig() {
        return new RoutingConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "queue-config.routing-with-key.routing-queue-2")
    public RoutingConfig secondRoutingQueueConfig() {
        return new RoutingConfig();
    }

    @Bean
    public Queue firstRoutingQueue(RoutingConfig firstRoutingQueueConfig) {
        return new Queue(firstRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public Queue secondRoutingQueue(RoutingConfig secondRoutingQueueConfig) {
        return new Queue(secondRoutingQueueConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }

    @Bean
    public Binding firstRoutingBinding(DirectExchange directExchange, Queue firstRoutingQueue,
            RoutingConfig firstRoutingQueueConfig) {
        return BindingBuilder
                .bind(firstRoutingQueue)
                .to(directExchange)
                .with(firstRoutingQueueConfig.getRoutingKey());
    }

    @Bean
    public Binding secondRoutingBinding(DirectExchange directExchange, Queue secondRoutingQueue,
            RoutingConfig secondRoutingQueueConfig) {
        return BindingBuilder
                .bind(secondRoutingQueue)
                .to(directExchange)
                .with(secondRoutingQueueConfig.getRoutingKey());
    }
}
