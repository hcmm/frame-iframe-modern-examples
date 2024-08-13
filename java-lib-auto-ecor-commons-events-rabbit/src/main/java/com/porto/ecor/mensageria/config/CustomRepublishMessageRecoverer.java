package com.porto.ecor.mensageria.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

public class CustomRepublishMessageRecoverer extends RepublishMessageRecoverer {

    public CustomRepublishMessageRecoverer(AmqpTemplate errorTemplate) {
        super(errorTemplate);
    }

    public CustomRepublishMessageRecoverer(AmqpTemplate errorTemplate, String errorExchange) {
        super(errorTemplate, errorExchange);
    }

    public CustomRepublishMessageRecoverer(AmqpTemplate errorTemplate, String errorExchange, String errorRoutingKey) {
        super(errorTemplate, errorExchange, errorRoutingKey);
    }

    @Override
    public void recover(Message message, Throwable cause) {
        String dynamicExchange = (String) message.getMessageProperties().getHeaders().get("x-dead-letter-exchange");
        String dynamicRoutingKey = (String) message.getMessageProperties().getHeaders().get("x-dead-letter-routing-key");

        if (dynamicExchange != null) {
            doSend(dynamicExchange, dynamicRoutingKey, message);
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Republishing failed message to exchange '" + dynamicExchange
                        + "' with routing key " + dynamicRoutingKey);
            }
        } else {
            super.recover(message, cause);
        }
    }
}
