package com.jetnuvem;

import com.jetnuvem.controller.HelloController;
import com.jetnuvem.message.RabbitMQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
public class HelloControllerUTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RabbitMQSender rabbitMQSender;

    @Test
    public void shouldReturnHelloFromEKS() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from EKS!"));
    }

    @Test
    public void shouldSendMessageToRabbitMQ() throws Exception {
        String message = "Test Message";
        
        mockMvc.perform(get("/api/send")
                .param("message", message))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent to RabbitMQ: " + message));
        
        verify(rabbitMQSender, times(1)).send(message);
    }
}

