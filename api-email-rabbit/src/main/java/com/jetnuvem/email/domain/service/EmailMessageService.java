package com.jetnuvem.email.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jetnuvem.email.domain.exception.EmailNaoEncontradaException;
import com.jetnuvem.email.domain.model.EmailMessage;
import com.jetnuvem.email.domain.repository.EmailMessageRepository;
import com.porto.ecor.mensageria.dto.DetalhesPedido;
import com.porto.ecor.mensageria.dto.Mensagem;
import com.porto.ecor.mensageria.dto.PedidoDTO;
import com.porto.ecor.mensageria.dto.PedidoInterface;
@Service
public class EmailMessageService {

	@Autowired
	private EmailMessageRepository emailRepository;

	@Autowired
	private EmailSendService emailSendService;

	@Transactional
	public EmailMessage criarEmailMessage(EmailMessage email) {
		return emailRepository.save(email);
	}

	@Transactional
	public EmailMessage salvar(EmailMessage email) {
		return emailRepository.save(email);
	}

	public EmailMessage buscarOuFalhar(UUID uuid) {
		return emailRepository.findById(uuid).orElseThrow(() -> new EmailNaoEncontradaException(uuid.toString()));
	}

	public List<EmailMessage> listarAplicacoes() {
		return emailRepository.findAll();
	}

	public EmailMessage buscarPorNome(String subject) {
		return emailRepository.findBySubject(subject);
	}

	@Transactional
	public void excluir(UUID uuid) {
		EmailMessage email = buscarOuFalhar(uuid);
		emailRepository.delete(email);
	}
	public void sendEmail1(Mensagem<PedidoDTO> tMensagem) throws Exception {
		System.out.println("Enviando email tipo1 para o pedido: " + tMensagem);
		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put("pedido", tMensagem);
		EmailMessage email = new EmailMessage();

		email.setText("teste");
		email.setSubject("Pedido criado");
		email.setTipo("email-templat");
		email.setToAddress(tMensagem.getCorpo().getCliente().getEmail());

		emailSendService.send(email, templateVariables);
		;
		salvar(email);

	}

	public void sendEmail2(Mensagem<PedidoInterface> tMensagem) throws Exception {
		System.out.println("Enviando email tipo2 para o pedido: " + tMensagem);
		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put("pedido", tMensagem);
		EmailMessage email = new EmailMessage();

		email.setText("teste");
		email.setSubject("Pedido criado");
		email.setTipo("email-templat");
		email.setToAddress(tMensagem.getCorpo().getCliente().getEmail());

		emailSendService.send(email, templateVariables);
		;
		salvar(email);

	}

	public void sendEmail3(Mensagem<DetalhesPedido<PedidoInterface>> tMensagem) {
		System.out.println("Enviando email tipo3 para o pedido: " + tMensagem);
		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put("pedido", tMensagem);
		EmailMessage email = new EmailMessage();

		email.setText("teste");
		email.setSubject("Pedido criado");
		email.setTipo("email-templat");
		email.setToAddress(tMensagem.getCorpo().getPedido().getCliente().getEmail());

		emailSendService.send(email, templateVariables);
		;
		salvar(email);
		
	}
}
