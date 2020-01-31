package com.ferratum.rabbit.api.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicRoutingConfig {

    private String name;
    private String routingPattern;
}
