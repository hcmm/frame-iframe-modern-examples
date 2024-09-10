package com.jetnuvem.pedido.domain.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jetnuvem.pedido.domain.model.Pedido;
import com.porto.ecor.mensageria.dto.DetalhesPedido;
import com.porto.ecor.mensageria.dto.Mensagem;
import com.porto.ecor.mensageria.dto.PedidoDTO;
import com.porto.ecor.mensageria.dto.PedidoInterface;
import com.porto.ecor.mensageria.dto.TrilhaAuditoria;

@Component
public class PedidoConverter {

    private final ModelMapper modelMapper;

    public PedidoConverter() {
        this.modelMapper = new ModelMapper();
    }

    public PedidoDTO toDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {
        return modelMapper.map(pedidoDTO, Pedido.class);
    }
    
    public DetalhesPedido<PedidoInterface> toDetalhesPedido(Pedido pedido) {
        PedidoDTO pedidoDTO = toDTO(pedido);
        DetalhesPedido<PedidoInterface> detalhesPedido = new DetalhesPedido<>();
        detalhesPedido.setPedido(pedidoDTO);
        return detalhesPedido;
    }

    public Mensagem<DetalhesPedido<PedidoInterface>> toMensagem(Pedido pedido, TrilhaAuditoria trilhaAuditoria) {
        DetalhesPedido<PedidoInterface> detalhesPedido = toDetalhesPedido(pedido);
        Mensagem<DetalhesPedido<PedidoInterface>> mensagem = new Mensagem<>();
        mensagem.setTrilhaAuditoria(trilhaAuditoria);
        mensagem.setCorpo(detalhesPedido);
        return mensagem;
    }
}

