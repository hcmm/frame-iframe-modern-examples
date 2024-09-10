package com.jetnuvem.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    @RabbitListener(queues = "your-queue-name", containerFactory="rabbitListenerContainerFactory")
    public void receive(String message) throws InterruptedException {
    	System.out.println("Received message: " + message);
    	Thread.sleep(10000);
        // Simulando erro para acionar o retry
        if (message.contains("retry")) {
            System.out.println("Simulating an error, triggering retry...");
            throw new RuntimeException("Error processing message: " + message);
        }

        System.out.println("Message processed successfully.");
    }
}