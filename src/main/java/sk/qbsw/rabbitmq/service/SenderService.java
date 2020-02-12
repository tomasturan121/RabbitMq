package sk.qbsw.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.rabbitmq.api.RoutingRequest;
import sk.qbsw.rabbitmq.client.Receiver;
import sk.qbsw.rabbitmq.client.Sender;

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

    public String readSimple() {
        return receiver.simpleRead();
    }

    public void sendSimple(RoutingRequest request) {
        sender.sendSimple(request.getMessageText());
    }

    public void publish(RoutingRequest request) {
        sender.publish(request.getRoutingKey(), request.getMessageText());
    }

    public void directRoute(RoutingRequest request) {
        sender.directRoute(request.getRoutingKey(), request.getMessageText());
    }

    public void topicRoute(RoutingRequest request) {
        sender.topicRoute(request.getRoutingKey(), request.getMessageText());
    }

    public void headerRoute(RoutingRequest request) {
        sender.headerRoute(request.getRoutingKey(), request.getMessageText(), request.getHeaderValue());
    }
}
