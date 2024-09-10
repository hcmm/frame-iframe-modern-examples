package com.jetnuvem.pedido;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PedidoRabbitConfig {

    // Nome da fila principal
    public static final String QUEUE_NAME = "pedido-queue";

    // Nome da Dead Letter Queue (DLQ)
    public static final String DLQ_NAME = "pedido-queue-dlq";

    // Nome da exchange principal
    public static final String EXCHANGE_NAME = "pedido-exchange";

    // Nome da Dead Letter Exchange (DLX)
    public static final String DLX_NAME = "pedido-dlx";

    // Routing key da fila principal
    public static final String ROUTING_KEY = "pedido-routing-key";

    // Routing key da DLQ
    public static final String DLQ_ROUTING_KEY = "pedido-dlq-routing-key";

    // Declaração da fila principal com DLX configurada
    @Bean
    public Queue pedidoQueue() {
        Map<String, Object> args = new HashMap<>();

        // Configuração para direcionar mensagens rejeitadas para a DLX
        args.put("x-dead-letter-exchange", DLX_NAME);
        args.put("x-dead-letter-routing-key", DLQ_ROUTING_KEY);

        // Criação da fila com argumentos
        return new Queue(QUEUE_NAME, true, false, false, args);
    }

    // Declaração da exchange principal
    @Bean
    public TopicExchange pedidoExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Binding da fila principal com a exchange
    @Bean
    public Binding pedidoBinding(Queue pedidoQueue, TopicExchange pedidoExchange) {
        return BindingBuilder.bind(pedidoQueue).to(pedidoExchange).with(ROUTING_KEY);
    }

    // Declaração da Dead Letter Queue (DLQ)
    @Bean
    public Queue pedidoDeadLetterQueue() {
        return new Queue(DLQ_NAME, true);
    }

    // Declaração da Dead Letter Exchange (DLX)
    @Bean
    public TopicExchange pedidoDeadLetterExchange() {
        return new TopicExchange(DLX_NAME);
    }

    // Binding da DLQ com a DLX
    @Bean
    public Binding pedidoDlqBinding(Queue pedidoDeadLetterQueue, TopicExchange pedidoDeadLetterExchange) {
        return BindingBuilder.bind(pedidoDeadLetterQueue).to(pedidoDeadLetterExchange).with(DLQ_ROUTING_KEY);
    }
}
