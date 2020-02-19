package sk.qbsw.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.rabbitmq.configuration.config.RoutingConfig;

@Configuration
public class HeadersRoutingConfiguration {

    public static final String HEADER_NAME = "header";

    @Bean
    @ConfigurationProperties(prefix = "queue-config.header-routing.header-routing-queue-1")
    public RoutingConfig firstHeadersRoutingConfig() {
        return new RoutingConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "queue-config.header-routing.header-routing-queue-2")
    public RoutingConfig secondHeadersRoutingConfig() {
        return new RoutingConfig();
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("headers_exchange");
    }

    @Bean
    public Queue firstHeadersRoutingQueue(RoutingConfig firstHeadersRoutingConfig) {
        return new Queue(firstHeadersRoutingConfig.getName(), Boolean.TRUE);
    }

    @Bean
    public Queue secondHeadersRoutingQueue(RoutingConfig secondHeadersRoutingConfig) {
        return new Queue(secondHeadersRoutingConfig.getName(), Boolean.TRUE);
    }


    @Bean
    public Binding firstHeaderRouteBinding(HeadersExchange headersExchange, Queue firstHeadersRoutingQueue,
            RoutingConfig firstHeadersRoutingConfig) {
        return BindingBuilder
                .bind(firstHeadersRoutingQueue)
                .to(headersExchange)
                .where(HEADER_NAME)
                .matches(firstHeadersRoutingConfig.getHeader());
    }

    @Bean
    public Binding secondHeaderRouteBinding(HeadersExchange headersExchange, Queue secondHeadersRoutingQueue,
            RoutingConfig secondHeadersRoutingConfig) {
        return BindingBuilder
                .bind(secondHeadersRoutingQueue)
                .to(headersExchange)
                .where(HEADER_NAME)
                .matches(secondHeadersRoutingConfig.getHeader());
    }


}
