package sk.qbsw.rabbitmq.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PublishSubscribeConfiguration {

    @Value("${queue-config.publish-subscribe.subscriber-queue-1.name}")
    private String firstSubscriberQueueName;

    @Value("${queue-config.publish-subscribe.subscriber-queue-2.name}")
    private String secondSubscriberQueueName;

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }

    @Bean
    public Queue firstSubscriberQueue() {
        return new Queue(firstSubscriberQueueName, Boolean.TRUE);
    }

    @Bean
    public Queue secondSubscriberQueue() {
        return new Queue(secondSubscriberQueueName, Boolean.TRUE);
    }

    @Bean
    public Binding firstSubscriberBinding(FanoutExchange fanoutExchange, Queue firstSubscriberQueue) {
        return BindingBuilder
                .bind(firstSubscriberQueue)
                .to(fanoutExchange);
    }

    @Bean
    public Binding secondSubscriberBinding(FanoutExchange fanoutExchange, Queue secondSubscriberQueue) {
        return BindingBuilder
                .bind(secondSubscriberQueue)
                .to(fanoutExchange);
    }
}
