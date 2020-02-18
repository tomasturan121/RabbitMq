package sk.qbsw.rabbitmq.api;

import lombok.Data;

@Data
public class SimpleReadRequest {

    private String queueName;
}
