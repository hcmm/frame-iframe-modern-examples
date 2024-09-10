package com.jetnuvem.pedido.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jetnuvem.pedido.domain.converter.PedidoConverter;
import com.jetnuvem.pedido.domain.exception.PedidoNaoEncontradaException;
import com.jetnuvem.pedido.domain.model.Pedido;
import com.jetnuvem.pedido.domain.repository.PedidoRepository;
import com.porto.ecor.mensageria.dto.CorrelationId;
import com.porto.ecor.mensageria.dto.DetalhesPedido;
import com.porto.ecor.mensageria.dto.Mensagem;
import com.porto.ecor.mensageria.dto.PedidoDTO;
import com.porto.ecor.mensageria.dto.PedidoInterface;
import com.porto.ecor.mensageria.dto.TrilhaAuditoria;
import com.porto.ecor.mensageria.enums.AplicacaoEnum;
import com.porto.ecor.mensageria.enums.EventoEnum;
import com.porto.ecor.mensageria.enums.StatusPropostaEnum;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RabbitTemplate amqpTemplate;

	
	@Autowired
    private PedidoConverter pedidoConverter;

	@Transactional
	public Pedido criarPedido(Pedido pedido) throws JsonProcessingException {
		if(pedido.getDescricao().contains("Pagamento")) {
			sendNotification2(pedido);
		} else { 
			sendNotification3(pedido);
		}
		return pedidoRepository.save(pedido);
	}

	@Transactional
	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Transactional
	public void excluir(Long pedidoId) {
		Pedido pedido = buscarOuFalhar(pedidoId);
		pedidoRepository.delete(pedido);
	}

	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradaException(pedidoId));
	}

	public List<Pedido> listarPedidos() {
		return pedidoRepository.findAll();
	}
	
	void sendNotification1(Pedido pedido) throws JsonProcessingException {
		String topicName = "proposta-input-topic";
    	CorrelationId correlationId = new CorrelationId(topicName);
        TrilhaAuditoria trilhaAuditoria = TrilhaAuditoria.builder()
                .numeroCotacao(1L)
                .numeroProposta(1L)
                .codigoMarca(1)
                .codigoSegmento(1)
                .idDocumentoJson(correlationId)
                .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                .dataCriacao(OffsetDateTime.now())
                .propostaStatusInicial(StatusPropostaEnum.INICIAL)
                .propostaStatusFinal(StatusPropostaEnum.RECEBIDA)
                .evento(EventoEnum.PROPOSTA_ENVIADA_CONSISTENCIA)
                .susep("P0029J")
                .build();

		
		PedidoDTO pedidoDTO = pedidoConverter.toDTO(pedido);

        Mensagem<PedidoDTO> mensagem = new Mensagem<>();
        mensagem.setTrilhaAuditoria(trilhaAuditoria);
        mensagem.setCorpo(pedidoDTO);
        
//        MessagePostProcessor messagePostProcessor = message -> {
//            message.getMessageProperties().setHeader("x-dead-letter-exchange", "dlx-exchange");
//            message.getMessageProperties().setHeader("x-dead-letter-routing-key", "email-dlx-routing-key");
//            return message;
//        };
        
//        amqpTemplate.convertAndSend("api-exchange", "email-routing-key", mensagem, messagePostProcessor);
        
        amqpTemplate.convertAndSend("pedido-exchange", "pedido-routing-key", mensagem);


        
	}

	void sendNotification2(Pedido pedido) throws JsonProcessingException {
    	String topicName = "proposta-input-topic";
    	CorrelationId correlationId = new CorrelationId(topicName);
        TrilhaAuditoria trilhaAuditoria = TrilhaAuditoria.builder()
                .numeroCotacao(1L)
                .numeroProposta(1L)
                .codigoMarca(1)
                .codigoSegmento(1)
                .idDocumentoJson(correlationId)
                .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                .dataCriacao(OffsetDateTime.now())
                .propostaStatusInicial(StatusPropostaEnum.INICIAL)
                .propostaStatusFinal(StatusPropostaEnum.RECEBIDA)
                .evento(EventoEnum.PROPOSTA_ENVIADA_CONSISTENCIA)
                .susep("P0029J")
                .build();
        
        
        Mensagem<DetalhesPedido<PedidoInterface>> mensagem = pedidoConverter.toMensagem(pedido, trilhaAuditoria);
        
        amqpTemplate.convertAndSend("pedido-exchange", "pedido-routing-key", mensagem);
    }
    
    void sendNotification3(Pedido pedido) throws JsonProcessingException {
    	String topicName = "proposta-input-topic";
    	CorrelationId correlationId = new CorrelationId(topicName);
		TrilhaAuditoria trilhaAuditoria = TrilhaAuditoria.builder()
                .numeroCotacao(1L)
                .numeroProposta(1L)
                .codigoMarca(1)
                .codigoSegmento(1)
                .idDocumentoJson(correlationId)
                .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                .dataCriacao(OffsetDateTime.now())
                .propostaStatusInicial(StatusPropostaEnum.RECEBIDA)
                .propostaStatusFinal(StatusPropostaEnum.A_CONSISTIR)
                .evento(EventoEnum.PROPOSTA_ENVIADA_CONSISTENCIA)
                .susep("P0029J")
                .build();

        Mensagem<DetalhesPedido<PedidoInterface>> mensagem = pedidoConverter.toMensagem(pedido, trilhaAuditoria);
//        MessagePostProcessor messagePostProcessor = message -> {
//            message.getMessageProperties().setHeader("x-dead-letter-exchange", "dlx-exchange");
//            message.getMessageProperties().setHeader("x-dead-letter-routing-key", "email-dlx-routing-key");
//            return message;
//        };
        
//        amqpTemplate.convertAndSend("api-exchange", "email-routing-key", mensagem, messagePostProcessor);
        amqpTemplate.convertAndSend("pedido-exchange", "pedido-routing-key", mensagem);
    }

}