package com.porto.ecor.mensageria.dto;

import lombok.Data;

@Data
public class DetalhesPedido<T> {
    private T pedido;
}
