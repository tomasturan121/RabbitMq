package sk.qbsw.rabbitmq.client;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;

import sk.qbsw.rabbitmq.configuration.HeadersRoutingConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class SenderTest {

    private static final String MESSAGE_TEXT = "messageText";
    private static final String ROUTING_KEY = "routingKey";

    private FanoutExchange fanoutExchange;
    private DirectExchange directExchange;
    private TopicExchange topicExchange;
    private HeadersExchange headersExchange;
    private Queue queue;
    @Mock
    private RabbitTemplate rabbitTemplate;
    private Sender sender;

    @Before
    public void setUp() {
        fanoutExchange = new FanoutExchange("fanout_exchange");
        directExchange = new DirectExchange("direct_exchange");
        topicExchange = new TopicExchange("topic_exchange");
        headersExchange = new HeadersExchange("headers_exchange");
        queue = new Queue("queue");

        sender = new Sender(fanoutExchange, directExchange, topicExchange, headersExchange, rabbitTemplate);
    }

//    @Test
//    public void sendSimpleTest() {
//        sender.sendSimple(MESSAGE_TEXT);
//
//        verify(rabbitTemplate).convertAndSend(queue.getName(), createTestMessage(null));
//        verifyNoMoreInteractions(rabbitTemplate);
//    }

    @Test
    public void publishTest() {
        sender.publish(ROUTING_KEY, MESSAGE_TEXT);

        verify(rabbitTemplate).convertAndSend(fanoutExchange.getName(), ROUTING_KEY, createTestMessage(null));
        verifyNoMoreInteractions(rabbitTemplate);
    }

    @Test
    public void directRouteTest() {
        sender.directRoute(ROUTING_KEY, MESSAGE_TEXT);

        verify(rabbitTemplate).convertAndSend(directExchange.getName(), ROUTING_KEY, createTestMessage(null));
        verifyNoMoreInteractions(rabbitTemplate);
    }

    @Test
    public void topicRouteTest() {
        sender.topicRoute(ROUTING_KEY, MESSAGE_TEXT);

        verify(rabbitTemplate).convertAndSend(topicExchange.getName(), ROUTING_KEY, createTestMessage(null));
        verifyNoMoreInteractions(rabbitTemplate);
    }

    @Test
    public void headersRouteTest() {
        final String headerValue = "headerValue";

        sender.headerRoute(null, MESSAGE_TEXT, headerValue);

        verify(rabbitTemplate).convertAndSend(headersExchange.getName(), null, createTestMessage(headerValue));
        verifyNoMoreInteractions(rabbitTemplate);
    }

    private static Message createTestMessage(String headerValue) {
        MessageProperties properties = new MessageProperties();

        properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        if (StringUtils.hasText(headerValue)) {
            properties.setHeader(HeadersRoutingConfiguration.HEADER_NAME, headerValue);
        }

        return new Message(MESSAGE_TEXT.getBytes(), properties);
    }
}
