package com.ferratum.rabbitMqProject.api;

import lombok.Data;

@Data
public class RouteRequest {

    private String routingKey;
    private String messageText;
}
