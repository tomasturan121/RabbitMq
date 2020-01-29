package com.ferratum.rabbitMqProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void send(String messageText) {
        sender.send(messageText);
    }
}
