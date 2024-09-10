package com.jetnuvem.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate amqpTemplate;

    private String exchange = "your-exchange";
    private String routingKey = "your-routing-key";

    public void send(String message) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }
}
