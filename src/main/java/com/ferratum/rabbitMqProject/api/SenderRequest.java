package com.ferratum.rabbitMqProject.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SenderRequest {

    private String textMessage;
}
