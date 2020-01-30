package com.ferratum.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ferratum.rabbit.api.config.QueueHeaderRoutingConfig;

@Configuration
public class HeadersRoutingConfiguration {

    public static final String HEADER_NAME = "header";

    @Bean
    @ConfigurationProperties(prefix = "queue-config.header-routing.header-routing-queue-1")
    public QueueHeaderRoutingConfig firstHeadersRoutingConfig() {
        return new QueueHeaderRoutingConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "queue-config.header-routing.header-routing-queue-2")
    public QueueHeaderRoutingConfig secondHeadersRoutingConfig() {
        return new QueueHeaderRoutingConfig();
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("headers_exchange", Boolean.TRUE, Boolean.FALSE);
    }

    @Bean
    public Queue firstHeadersRoutingQueue(QueueHeaderRoutingConfig firstHeadersRoutingConfig) {
        return new Queue(firstHeadersRoutingConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public Queue secondHeadersRoutingQueue(QueueHeaderRoutingConfig secondHeadersRoutingConfig) {
        return new Queue(secondHeadersRoutingConfig.getName(), Boolean.TRUE);
    }


    @Bean
    public Binding firstHeaderRouteBinding(HeadersExchange headersExchange, Queue firstHeadersRoutingQueue,
            QueueHeaderRoutingConfig firstHeadersRoutingConfig) {
        return BindingBuilder.bind(firstHeadersRoutingQueue).to(headersExchange).where(HEADER_NAME)
                .matches(firstHeadersRoutingConfig.getHeader());
    }

    @Bean
    public Binding secondHeaderRouteBinding(HeadersExchange headersExchange, Queue secondHeadersRoutingQueue,
            QueueHeaderRoutingConfig secondHeadersRoutingConfig) {
        return BindingBuilder.bind(secondHeadersRoutingQueue).to(headersExchange).where(HEADER_NAME)
                .matches(secondHeadersRoutingConfig.getHeader());
    }


}
