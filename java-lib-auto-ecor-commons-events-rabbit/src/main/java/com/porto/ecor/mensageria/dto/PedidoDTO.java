package com.porto.ecor.mensageria.dto;

import lombok.Data;

@Data
public class PedidoDTO implements PedidoInterface {

    private Long id;
    private String descricao;
    private double valor;
    private ClienteDTO cliente;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public double getValor() {
        return valor;
    }

    @Override
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public ClienteDTO getCliente() {
        return cliente;
    }

    @Override
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

}
