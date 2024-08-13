package com.jetnuvem.pedido.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jetnuvem.pedido.api.assembler.PedidoInputDisassembler;
import com.jetnuvem.pedido.api.assembler.PedidoModelAssembler;
import com.jetnuvem.pedido.api.input.PedidoInput;
import com.jetnuvem.pedido.api.model.PedidoModel;
import com.jetnuvem.pedido.domain.model.Pedido;
import com.jetnuvem.pedido.domain.repository.PedidoRepository;
import com.jetnuvem.pedido.domain.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) throws JsonProcessingException {
		Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		pedido = pedidoService.criarPedido(pedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	
	@PutMapping("/{pedidoId}")
	public PedidoModel atualizar(@PathVariable Long pedidoId,
			@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido pedidoAtual = pedidoService.buscarOuFalhar(pedidoId);
		pedidoInputDisassembler.copyToDomainObject(pedidoInput, pedidoAtual);
		pedidoAtual = pedidoService.salvar(pedidoAtual);
		
		return pedidoModelAssembler.toModel(pedidoAtual);
	}
	
	@DeleteMapping("/{pedidoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remover(@PathVariable Long pedidoId) {
		pedidoService.excluir(pedidoId);
		return ResponseEntity.noContent().build();
	}
	
	
	
	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@GetMapping
	public PagedModel<PedidoModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Pedido> pedidosPage = pedidoRepository.findAll(pageable);
		
		PagedModel<PedidoModel> pedidoesPagedModel = pagedResourcesAssembler
				.toModel(pedidosPage, pedidoModelAssembler);
		
		return pedidoesPagedModel;
	}
	
}
