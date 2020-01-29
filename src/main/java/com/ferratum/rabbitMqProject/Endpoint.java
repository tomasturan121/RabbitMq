package com.ferratum.rabbitMqProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferratum.rabbitMqProject.api.SenderRequest;
import com.ferratum.rabbitMqProject.service.SenderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("rabbit")
public class Endpoint {

    private final SenderService senderService;

    @Autowired
    public Endpoint(SenderService senderService) {
        this.senderService = senderService;
    }

    @PostMapping("/send")
    public void send(@RequestBody SenderRequest request) {
        log.info("Calling /send with request: {}", request);
        senderService.send(request.getTextMessage());
        log.info("Call to /send with request: {} successful", request);
    }
}
