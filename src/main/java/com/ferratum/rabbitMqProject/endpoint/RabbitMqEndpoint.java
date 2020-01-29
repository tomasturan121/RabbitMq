package com.ferratum.rabbitMqProject.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferratum.rabbitMqProject.api.ReceiverResponse;
import com.ferratum.rabbitMqProject.api.SenderRequest;
import com.ferratum.rabbitMqProject.service.RabbitMqService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("rabbit")
public class RabbitMqEndpoint {

    private final RabbitMqService rabbitMqService;

    @Autowired
    public RabbitMqEndpoint(RabbitMqService rabbitMqService) {
        this.rabbitMqService = rabbitMqService;
    }

    @PostMapping("/send")
    public ReceiverResponse send(@RequestBody SenderRequest request) {
        log.info("Calling /send with request: {}", request);
        String result = rabbitMqService.send(request.getTextMessage());
        log.info("Call to /send with request: {} successful", request);
        return new ReceiverResponse(result);
    }
}
