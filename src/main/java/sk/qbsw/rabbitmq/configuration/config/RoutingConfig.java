package sk.qbsw.rabbitmq.configuration.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoutingConfig {

    private String name;
    private String routingKey;
    private String routingPattern;
    private String header;
}
