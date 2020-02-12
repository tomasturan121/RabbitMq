package sk.qbsw.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.qbsw.rabbitmq.api.RoutingRequest;
import sk.qbsw.rabbitmq.service.SenderService;

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

    @GetMapping("/readSimple")
    public String readSimple() {
        return senderService.readSimple();
    }

    @PostMapping("/sendSimple")
    public void send(@RequestBody RoutingRequest request) {
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
