package com.jetnuvem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jetnuvem.message.RabbitMQSender;

@RestController
@RequestMapping("/api")
public class HelloController {
	
	@Autowired
    private RabbitMQSender rabbitMQSender;
	
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from EKS!");
    }
    
    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rabbitMQSender.send(message);
        return "Message sent to RabbitMQ: " + message;
    }
    
}

