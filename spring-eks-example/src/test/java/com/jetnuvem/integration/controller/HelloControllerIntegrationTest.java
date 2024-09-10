package com.jetnuvem.integration.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.8-management")
            .withExposedPorts(5672, 15672) // Expondo as portas no container, mas no host serão dinâmicas
            .withReuse(true);

    @DynamicPropertySource
    static void configureRabbitMQ(DynamicPropertyRegistry registry) {
    	 registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
         registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
         registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
         registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }


    @Test
    void testHelloEndpoint() throws Exception {
    	 mockMvc.perform(get("/api/hello"))
         .andExpect(status().isOk())
         .andExpect(content().string("Hello from EKS!"));
    }

    @Test
    void testSendMessageToRabbitMQ() throws Exception {
        String message = "Test message";

        mockMvc.perform(get("/api/send")
                .param("message", message)
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(content().string("Message sent to RabbitMQ: " + message)); // Verifica o conteúdo da resposta

    }

}
