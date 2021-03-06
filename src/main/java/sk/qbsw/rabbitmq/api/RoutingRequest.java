package sk.qbsw.rabbitmq.api;

import lombok.Data;

@Data
public class RoutingRequest {

    private String routingKey;
    private String messageText;
    private String headerValue;
}
