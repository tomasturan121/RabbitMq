package com.ferratum.rabbitMqProject.api.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QueueRoutingConfig {

    private String name;
    private String routingKey;
}
