package com.ferratum.rabbitMqProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferratum.rabbitMqProject.api.RouteRequest;
import com.ferratum.rabbitMqProject.client.Sender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SenderService {

    private final Sender sender;

    @Autowired
    public SenderService(Sender sender) {
        this.sender = sender;
    }

    public void publish(String messageText) {
        sender.publish(messageText);
    }

    public void route(RouteRequest request) {
        sender.route(request.getRoutingKey(), request.getMessageText());
    }
}
