package com.porto.ecor.mensageria.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("EventoEnumTest")
class EventoEnumTest {

    @Test
    void retornarDescricao() {
        assertThat(EventoEnum.PROPOSTA_RECEBIDA.getDescricao()).isNotNull().isNotNull();
        assertThat(EventoEnum.PROPOSTA_ENVIADA_CONSISTENCIA.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_RECEBIDA_CONSISTENCIA.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_CONSISTIDA_SEM_CRITICAS.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_CONSISTIDA_COM_CRITICAS.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_CONSISTIDA_COM_RECUSA.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_ENVIADA_WORKFLOW.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_RECEBIDA_WORKFLOW.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_ENVIADA_EMISSAO.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_RECEBIDA_EMISSAO.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_ENVIADA_FINANCEIRO.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_RECEBIDA_FINANCEIRO.getDescricao()).isNotNull();
        assertThat(EventoEnum.APOLICE_EMITIDA.getDescricao()).isNotNull();
        assertThat(EventoEnum.PROPOSTA_RECUSADA.getDescricao()).isNotNull();
    }
}
