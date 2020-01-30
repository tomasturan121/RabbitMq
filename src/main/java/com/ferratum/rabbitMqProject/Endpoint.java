package com.ferratum.rabbitMqProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferratum.rabbitMqProject.api.RouteRequest;
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

    @PostMapping("/publish")
    public void publish(@RequestBody RouteRequest request) {
        senderService.publish(request.getMessageText());
    }

    @PostMapping("/route")
    public void route(@RequestBody RouteRequest request) {
        senderService.route(request);
    }

    @PostMapping("/topicRoute")
    public void topicRoute(@RequestBody RouteRequest request) {
        senderService.topicRoute(request);
    }
}
