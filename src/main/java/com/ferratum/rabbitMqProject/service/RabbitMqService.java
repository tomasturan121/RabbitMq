package com.ferratum.rabbitMqProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferratum.rabbitMqProject.client.ReceiverFromRabbitMq;
import com.ferratum.rabbitMqProject.client.SenderToRabbitMq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMqService {

    private final SenderToRabbitMq senderToRabbitMq;
    private final ReceiverFromRabbitMq receiverFromRabbitMq;

    @Autowired
    public RabbitMqService(SenderToRabbitMq senderToRabbitMq, ReceiverFromRabbitMq receiverFromRabbitMq) {
        this.senderToRabbitMq = senderToRabbitMq;
        this.receiverFromRabbitMq = receiverFromRabbitMq;
    }

    public String send(String messageText) {
        senderToRabbitMq.send(messageText);
        return receiverFromRabbitMq.listen();
    }
}
