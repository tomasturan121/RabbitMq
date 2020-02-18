package sk.qbsw.rabbitmq.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import sk.qbsw.rabbitmq.configuration.HeadersRoutingConfiguration;

@Slf4j
@Component
public class Sender {

    private final FanoutExchange fanoutExchange;
    private final DirectExchange directExchange;
    private final TopicExchange topicExchange;
    private final HeadersExchange headersExchange;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Sender(
            FanoutExchange fanoutExchange,
            DirectExchange directExchange,
            TopicExchange topicExchange,
            HeadersExchange headersExchange,
            RabbitTemplate rabbitTemplate) {
        this.fanoutExchange = fanoutExchange;
        this.directExchange = directExchange;
        this.topicExchange = topicExchange;
        this.headersExchange = headersExchange;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * sending with default exchange
     *
     * @param messageText message payload
     */
    public void sendSimple(String queueName, Integer priority, String messageText) {
        log.info("Sending message with text: {}", messageText);
        final Message message = new Message(messageText.getBytes(), createProperties(null, priority));

        rabbitTemplate.convertAndSend(queueName, message);
        log.info("Message with text: {} has been send to Queue: {} of RabbitMQ", messageText, queueName);
    }

    /**
     * publishing with fanout exchange
     *
     * @param routingKey routing key - ignored in this case, message is being routed to all bound queues
     * @param messageText message payload
     */
    public void publish(String routingKey, String messageText) {
        log.info("Publishing message with text: {}", messageText);
        final Message message = new Message(messageText.getBytes(), createProperties(null, null));

        rabbitTemplate.convertAndSend(fanoutExchange.getName(), routingKey, message);
        log.info("Message with text: {} has been published to Fanout Exchange of RabbitMQ", messageText);
    }

    /**
     * routing according to routing key value with direct exchange
     *
     * @param routingKey routing key for direct exchange object
     * @param messageText message payload
     */
    public void directRoute(String routingKey, String messageText) {
        log.info("Sending message with routing key: {} and text: {}", routingKey, messageText);
        final Message message = new Message(messageText.getBytes(), createProperties(null, null));

        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, message);
        log.info("Message with routing key: {} and text: {} has been sent to Direct Exchange RabbitMQ", routingKey,
                messageText);
    }

    /**
     * routing according to routing pattern with topic exchange
     *
     * @param routingKey routing key for topic exchange object
     * @param messageText message payload
     */
    public void topicRoute(String routingKey, String messageText) {
        log.info("Sending message with routing key: {} and text: {} to Topic Exchange", routingKey, messageText);
        final Message message = new Message(messageText.getBytes(), createProperties(null, null));

        rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, message);
        log.info("Message with routing key: {} and text: {} has been sent to Topic Exchange of RabbitMQ", routingKey,
                messageText);
    }

    /**
     * routing according to value from message headers with headers exchange
     *
     * @param routingKey routing key - ignored in this case, routing is done through header value
     * @param messageText message payload
     * @param headerValue header value for headers exchange object
     */
    public void headerRoute(String routingKey, String messageText, String headerValue) {
        log.info("Sending message with header value: {} and text: {} to Headers Exchange", headerValue, messageText);
        final Message message = new Message(messageText.getBytes(), createProperties(headerValue, null));

        rabbitTemplate.convertAndSend(headersExchange.getName(), routingKey, message);
        log.info("Message with header value: {} and text: {} has been sent to Headers Exchange of RabbitMQ",
                headerValue, messageText);
    }

    /**
     * method for creating simple message properties
     *
     * @param headerValue value of header
     * @return simple MessageProperties object
     */
    private static MessageProperties createProperties(String headerValue, Integer priority) {
        final MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        // setting message priority
        if (priority != null) {
            messageProperties.setPriority(priority);
        }

        if (StringUtils.hasText(headerValue)) {
            messageProperties.setHeader(HeadersRoutingConfiguration.HEADER_NAME, headerValue);
        }

        return messageProperties;
    }
}
