package com.porto.ecor.mensageria.config.rabbit;

import java.util.Map;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
public class RabbitMQConnectionFactory {

    @Value("${rabbitmq.secret.name:local}")
    private String secretName;
    
    @Value("${spring.rabbitmq.host:localhost}")
    private String defaultHost;

    @Value("${spring.rabbitmq.port:5672}")
    private int defaultPort;

    @Value("${spring.rabbitmq.username:porto}")
    private String defaultUsername;

    @Value("${spring.rabbitmq.password:porto}")
    private String defaultPassword;
    
    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    private final SecretsManagerClient secretsManagerClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RabbitMQConnectionFactory(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }
    
    @Bean
    public RabbitConnectionFactoryBean rabbitConnectionFactoryBean() throws Exception {
        RabbitConnectionFactoryBean factoryBean = new RabbitConnectionFactoryBean();
        
        if (secretName.equalsIgnoreCase("local")) {
            // Configurações para ambiente local
            factoryBean.setHost(defaultHost);
            factoryBean.setPort(defaultPort);
            factoryBean.setUsername(defaultUsername);
            factoryBean.setPassword(defaultPassword);
        } else {
            // Busca as credenciais do Secret Manager para ambientes não-locais
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();
            GetSecretValueResponse getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
            String secretString = getSecretValueResponse.secretString();
    
            Map<String, String> secretMap = objectMapper.readValue(secretString, new TypeReference<Map<String, String>>() {});
    
            factoryBean.setHost(secretMap.getOrDefault("rabbitmq.host", defaultHost));
            factoryBean.setUsername(secretMap.getOrDefault("rabbitmq.username", defaultUsername));
            factoryBean.setPassword(secretMap.getOrDefault("rabbitmq.password", defaultPassword));
            factoryBean.setVirtualHost(secretMap.getOrDefault("rabbitmq.virtual-host", "/"));

            // Somente habilite SSL em ambientes não-locais, caso necessário
            factoryBean.setUseSSL(true);
            factoryBean.setSslAlgorithm("TLSv1.2");
        }
        
        return factoryBean;
    }
    
    @Bean
    public ConnectionFactory rabbitConnectionFactory(RabbitConnectionFactoryBean rabbitConnectionFactoryBean) throws Exception {
        // Criação da CachingConnectionFactory
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitConnectionFactoryBean.getObject());

        
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL); // Configurações de cache e controle de conexões/canais
        cachingConnectionFactory.setConnectionLimit(1);   // Limitar para 1 conexão por instancia de servico
        cachingConnectionFactory.setChannelCheckoutTimeout(30000);  // Timeout de checkout de canal de 5 segundos
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);// Permite correlacionar mensagens de confirmação com as mensagens enviadas.
        cachingConnectionFactory.setPublisherReturns(true);  // Habilita retornos de mensagens não roteáveis
        cachingConnectionFactory.setRequestedHeartBeat(60);  // Heartbeat de 60 segundos
        cachingConnectionFactory.setConnectionTimeout(30000);  // Timeout de conexão de 30 segundos

        // Definir o nome da conexão para exibição no RabbitMQ
        cachingConnectionFactory.setConnectionNameStrategy(connectionFactoryBean -> "MyRabbitMQConnection");

        return cachingConnectionFactory;
    }
}
