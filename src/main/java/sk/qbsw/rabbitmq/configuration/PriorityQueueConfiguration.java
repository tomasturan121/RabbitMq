package sk.qbsw.rabbitmq.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriorityQueueConfiguration {

    @Bean
    public Queue priorityQueue() {
        Map<String, Object> args = new HashMap<>();
        // setting max priority to 10
        args.put("x-max-priority", 10);
        return new Queue("priority_queue", true, false, false, args);
    }
}
