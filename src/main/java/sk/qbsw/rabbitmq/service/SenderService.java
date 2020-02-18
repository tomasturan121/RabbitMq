package sk.qbsw.rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sk.qbsw.rabbitmq.api.RoutingRequest;
import sk.qbsw.rabbitmq.api.SimpleReadRequest;
import sk.qbsw.rabbitmq.api.SimpleSenderRequest;
import sk.qbsw.rabbitmq.client.Receiver;
import sk.qbsw.rabbitmq.client.Sender;

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

    public String readSimple(SimpleReadRequest request) {
        return receiver.simpleRead(request.getQueueName());
    }

    public void sendSimple(SimpleSenderRequest request) {
        sender.sendSimple(request.getQueueName(), request.getPriority(), request.getMessageText());
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
