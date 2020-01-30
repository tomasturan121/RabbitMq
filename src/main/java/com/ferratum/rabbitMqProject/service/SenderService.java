package com.ferratum.rabbitMqProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferratum.rabbitMqProject.api.RouteRequest;
import com.ferratum.rabbitMqProject.client.Receiver;
import com.ferratum.rabbitMqProject.client.Sender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SenderService {

    private final Sender sender;
    private final Receiver receiver;

    @Autowired
    public SenderService(Sender sender, Receiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void sendSimple(String messageText) {
        sender.sendSimple(messageText);
    }

    public String readSimple() {
        return receiver.simpleListen();
    }

    public void publish(String messageText) {
        sender.publish(messageText);
    }

    public void route(RouteRequest request) {
        sender.route(request.getRoutingKey(), request.getMessageText());
    }

    public void topicRoute(RouteRequest request) {
        sender.topicRoute(request.getRoutingKey(), request.getMessageText());
    }
}
