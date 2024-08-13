package com.jetnuvem.pedido;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.porto.ecor.mensageria.config.ExchangeFactory;

@Configuration
public class PedidoRabbitConfig {

    private final ExchangeFactory exchangeFactory;

    @Autowired
    public PedidoRabbitConfig(ExchangeFactory exchangeFactory) {
        this.exchangeFactory = exchangeFactory;
    }

    @Bean
    public TopicExchange apiExchange() {
        return exchangeFactory.createExchange("api-exchange");
    }
    
}

