package com.porto.ecor.mensageria.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeFactory {

    private final Queue eventQueue;
    private final RabbitAdmin rabbitAdmin;

    @Autowired
    public ExchangeFactory(@Qualifier("eventListenerQueue") Queue eventQueue, RabbitAdmin rabbitAdmin) {
        this.eventQueue = eventQueue;
        this.rabbitAdmin = rabbitAdmin;
    }

    @Bean
    public TopicExchange createExchange(String exchangeName) {
        TopicExchange exchange = new TopicExchange(exchangeName, true, false);
        Binding binding = BindingBuilder.bind(eventQueue).to(exchange).with("#");
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(binding);
        return exchange;
    }

}
