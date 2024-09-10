package com.porto.ecor.mensageria.config.rabbit;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.porto.ecor.mensageria.dto.PedidoInterface;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Retry Policy
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);  // Tentar até 5 vezes

        // Exponential BackOff Policy
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2000);  // Começa com 2 segundos
        backOffPolicy.setMultiplier(2.0);  // Dobra o intervalo a cada tentativa
        backOffPolicy.setMaxInterval(30000);  // Intervalo máximo de 30 segundos

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
    
    @Bean
    public RetryOperationsInterceptor rabbitRetryInterceptor(RetryTemplate retryTemplate) {
        return RetryInterceptorBuilder.stateless()
                .retryOperations(retryTemplate)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper pedidoObjectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);  // Retorna mensagens não roteáveis
        rabbitTemplate.setRetryTemplate(retryTemplate());  // Retry com backoff exponencial
        rabbitTemplate.setMessageConverter(jsonMessageConverter(pedidoObjectMapper));
        
        return rabbitTemplate;
    }
    
   

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                            RabbitTemplate rabbitTemplate,
                                                                            RetryTemplate retryTemplate,
                                                                            RetryOperationsInterceptor rabbitRetryInterceptor,
                                                                            @Qualifier("pedidoObjectMapper") ObjectMapper pedidoObjectMapper) {
        // Cria uma fábrica de containers para listeners do RabbitMQ
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        
        // Define a connection factory, responsável por gerenciar as conexões com o RabbitMQ
        factory.setConnectionFactory(connectionFactory);
        
        // Define o número máximo de consumidores simultâneos
        factory.setMaxConcurrentConsumers(5);
        
        // Define o número inicial de consumidores simultâneos
        factory.setConcurrentConsumers(5);
        
        // Define o número de mensagens pré-carregadas por consumidor antes de processar
        factory.setPrefetchCount(10);
        
        // Desabilita o reenvio automático de mensagens rejeitadas para a fila, evitando loops infinitos
        factory.setDefaultRequeueRejected(false);
        
        // Define o modo de reconhecimento das mensagens (ACK) como automático
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        
        // Define o tempo máximo de espera para o recebimento de mensagens (em milissegundos)
        factory.setReceiveTimeout(20000L); // 20 segundos
        
        // Define o conversor de mensagens para JSON, usando o ObjectMapper especificado
        factory.setMessageConverter(jsonMessageConverter(pedidoObjectMapper));
        
        // Define uma cadeia de interceptores para aplicar políticas de retry e recuperação
        factory.setAdviceChain(rabbitRetryInterceptor);
        
        // Retorna a fábrica de containers configurada
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper pedidoObjectMapper) {
        return new Jackson2JsonMessageConverter(pedidoObjectMapper);
    }

    @Bean
    @Qualifier("pedidoObjectMapper")
    public ObjectMapper pedidoObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(PedidoInterface.class).build();
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE,
                JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }
}
