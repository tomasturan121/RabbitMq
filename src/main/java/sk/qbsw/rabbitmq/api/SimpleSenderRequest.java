package sk.qbsw.rabbitmq.api;

import lombok.Data;

@Data
public class SimpleSenderRequest extends RoutingRequest {

    private String queueName;
    private Integer priority;
}
