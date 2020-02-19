# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)

## RabbitMQ installation
* download: https://www.rabbitmq.com/download.html
* installation guide: https://www.rabbitmq.com/install-windows.html
* enable management plugin `rabbitmq-plugins enable rabbitmq_management`

## How to run RabbitMQ project
* `mvn clean install`
* `Run application via RabbitApplication.java`

### Endpoints
* [/readSimple](http://localhost:8080/rabbit/readSimple) - simple method for reading message from queue
* [/sendSimple](http://localhost:8080/rabbit/sendSimple) - simple method for writing message to queue
* [/publish](http://localhost:8080/rabbit/publish) - simple method for publishing (fanout exchange)
* [/directRoute](http://localhost:8080/rabbit/directRoute) - simple method for direct routing (direct exchange) 
* [/topicRoute](http://localhost:8080/rabbit/topicRoute) - simple method for topic routing (topic exchange)
* [/headersRoute](http://localhost:8080/rabbit/headersRoute) - simple method for headers routing (headers exchange)

### Management plugin
* [/managementPlugin](http://localhost:15672) - RabbitMQ management plugin
