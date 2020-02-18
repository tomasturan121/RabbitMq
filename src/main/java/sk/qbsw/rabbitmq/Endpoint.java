package sk.qbsw.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import sk.qbsw.rabbitmq.api.RoutingRequest;
import sk.qbsw.rabbitmq.api.SimpleReadRequest;
import sk.qbsw.rabbitmq.api.SimpleSenderRequest;
import sk.qbsw.rabbitmq.service.SenderService;

@Slf4j
@RestController
@RequestMapping("rabbit")
public class Endpoint {

    private final SenderService senderService;

    @Autowired
    public Endpoint(SenderService senderService) {
        this.senderService = senderService;
    }

    @PostMapping("/readSimple")
    public String readSimple(@RequestBody SimpleReadRequest request) {
        return senderService.readSimple(request);
    }

    @PostMapping("/sendSimple")
    public void send(@RequestBody SimpleSenderRequest request) {
        senderService.sendSimple(request);
    }

    @PostMapping("/publish")
    public void publish(@RequestBody RoutingRequest request) {
        senderService.publish(request);
    }

    @PostMapping("/directRoute")
    public void route(@RequestBody RoutingRequest request) {
        senderService.directRoute(request);
    }

    @PostMapping("/topicRoute")
    public void topicRoute(@RequestBody RoutingRequest request) {
        senderService.topicRoute(request);
    }

    @PostMapping("/headersRoute")
    public void headersRoute(@RequestBody RoutingRequest request) {
        senderService.headerRoute(request);
    }
}
