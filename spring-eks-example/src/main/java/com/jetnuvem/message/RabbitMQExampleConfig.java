package com.jetnuvem.message;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQExampleConfig {

    // Nome da fila principal
    public static final String QUEUE_NAME = "your-queue-name";
    
    // Nome da Dead Letter Queue (DLQ)
    public static final String DLQ_NAME = "your-queue-name-dlq";
    
    // Nome da exchange principal
    public static final String EXCHANGE_NAME = "your-exchange";
    
    // Nome da Dead Letter Exchange (DLX)
    public static final String DLX_NAME = "your-dlx";

    // Routing key da fila principal
    public static final String ROUTING_KEY = "your-routing-key";
    
    // Routing key da DLQ
    public static final String DLQ_ROUTING_KEY = "your-dlq-routing-key";

    // Declaração da fila principal com DLX configurada
    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<>();
        
        // Configuração para direcionar mensagens rejeitadas para a DLX
        args.put("x-dead-letter-exchange", DLX_NAME);
        args.put("x-dead-letter-routing-key", DLQ_ROUTING_KEY);
        
        // Criação da fila com argumentos
        return new Queue(QUEUE_NAME, true, false, false, args);
    }

    // Declaração da exchange principal
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Binding da fila principal com a exchange
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // Declaração da Dead Letter Queue (DLQ)
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_NAME, true);
    }

    // Declaração da Dead Letter Exchange (DLX)
    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX_NAME);
    }

    // Binding da DLQ com a DLX
    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DLQ_ROUTING_KEY);
    }
}
