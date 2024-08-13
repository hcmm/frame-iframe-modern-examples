package com.jetnuvem.email;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.porto.ecor.mensageria.config.ExchangeFactory;
import com.porto.ecor.mensageria.config.QueueFactory;

@Configuration
public class EmailRabbitConfig {

    private final QueueFactory queueFactory;
    private final ExchangeFactory exchangeFactory;
    private final RabbitAdmin rabbitAdmin;

    @Autowired
    public EmailRabbitConfig(ExchangeFactory exchangeFactory, QueueFactory queueFactory, RabbitAdmin rabbitAdmin) {
        this.exchangeFactory = exchangeFactory;
        this.queueFactory = queueFactory;
        this.rabbitAdmin = rabbitAdmin;
    }

    @Bean(name = "apiExchange")
    public TopicExchange apiExchange() {
        return exchangeFactory.createExchange("api-exchange");
    }

    @Bean(name = "dlxExchange")
    public TopicExchange dlxExchange() {
        return exchangeFactory.createExchange("dlx-exchange");
    }

    @Bean(name = "emailQueue")
    public Queue emailQueue() {
        return queueFactory.createQueue("api-email-queue", Map.of(
                "x-dead-letter-exchange", "dlx-exchange",
                "x-dead-letter-routing-key", "email-dlx-routing-key"
       ));
    }

    @Bean(name = "emailDLQ")
    public Queue emailDLQ() {
         Queue dlq = queueFactory.createDLQ("api-email-dlq");
         rabbitAdmin.declareQueue(dlq);
         return dlq;
    }

    @Bean
    public Binding emailBinding(@Qualifier("apiExchange") TopicExchange apiExchange,
                                @Qualifier("emailQueue") Queue emailQueue) {
        Binding binding = BindingBuilder.bind(emailQueue).to(apiExchange).with("email-routing-key");
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Binding emailDLQBinding(@Qualifier("dlxExchange") TopicExchange dlxExchange,
                                   @Qualifier("emailDLQ") Queue emailDLQ) {
        Binding binding = BindingBuilder.bind(emailDLQ).to(dlxExchange).with("email-dlx-routing-key");
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}
