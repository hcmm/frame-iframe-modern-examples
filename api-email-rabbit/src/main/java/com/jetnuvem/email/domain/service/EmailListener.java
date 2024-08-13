package com.jetnuvem.email.domain.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.porto.ecor.mensageria.dto.DetalhesPedido;
import com.porto.ecor.mensageria.dto.Mensagem;
import com.porto.ecor.mensageria.dto.PedidoInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailListener {


	@Autowired
	EmailMessageService mailService;

	
	/**
     * Processa as mensagens de pedidos da fila de emails.
     *
     * @param pedido O pedido recebido.
     */
    @RabbitListener(queues = "#{emailQueue.name}", containerFactory = "rabbitListenerContainerFactory")
    public void processarPedido(Message<Mensagem<DetalhesPedido<PedidoInterface>>> message) {
        log.info("Recebida mensagem na fila de emails: {}", message.getPayload());

    	mailService.sendEmail3(message.getPayload());
    }

}
