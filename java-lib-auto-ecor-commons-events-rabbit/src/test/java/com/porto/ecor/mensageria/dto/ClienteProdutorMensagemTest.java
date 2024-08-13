package com.porto.ecor.mensageria.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.porto.ecor.mensageria.dto.corpo.MotivoRecusa;
import com.porto.ecor.mensageria.dto.corpo.Proposta;
import com.porto.ecor.mensageria.dto.corpo.PropostaRecebida;
import com.porto.ecor.mensageria.dto.corpo.PropostaRecusadaBurocratica;
import com.porto.ecor.mensageria.dto.corpo.factory.PropostaFactory;
import com.porto.ecor.mensageria.enums.AplicacaoEnum;
import com.porto.ecor.mensageria.enums.EventoEnum;
import com.porto.ecor.mensageria.enums.StatusPropostaEnum;

@DisplayName("ClienteProdutorMensagemTest")
public class ClienteProdutorMensagemTest {

    @Test
    void alteracaoStatusRecebidaParaConsistir() throws Exception {

        TrilhaAuditoria trilhaAuditoria = TrilhaAuditoria.builder()
                .numeroCotacao(1L)
                .numeroProposta(1L)
                .codigoMarca(1)
                .codigoSegmento(1)
                .idDocumentoJson(new CorrelationId())
                .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                .dataCriacao(OffsetDateTime.now())
                .propostaStatusInicial(StatusPropostaEnum.RECEBIDA)
                .propostaStatusFinal(StatusPropostaEnum.A_CONSISTIR)
                .evento(EventoEnum.PROPOSTA_ENVIADA_CONSISTENCIA)
                .susep("P0029J")
                .build();

        // Criacao do DTO de Proposta de acordo com o status
        Proposta proposta = PropostaFactory.getCorpoMensagem(StatusPropostaEnum.RECEBIDA);
        proposta.setStatusProposta(StatusPropostaEnum.A_CONSISTIR);
        proposta.setNumeroProposta(1L);
        proposta.setNumeroCotacao(1L);
        proposta.setIdDocumentoJson(new CorrelationId());

        Mensagem mensagem = new Mensagem<PropostaRecebida>();
        mensagem.setTrilhaAuditoria(trilhaAuditoria);
        mensagem.setCorpo(proposta);

        assertThat(mensagem.getTrilhaAuditoria()).isNotNull();
        assertThat(mensagem.getCorpo()).isNotNull();
    }

    @Test
    void alteracaoStatusConsistindoParaRecusaBurocratica() throws Exception {

        TrilhaAuditoria trilhaAuditoria = TrilhaAuditoria.builder()
                .numeroCotacao(1L)
                .numeroProposta(1L)
                .codigoMarca(1)
                .codigoSegmento(1)
                .idDocumentoJson(new CorrelationId())
                .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                .dataCriacao(OffsetDateTime.now())
                .propostaStatusInicial(StatusPropostaEnum.CONSISTINDO)
                .propostaStatusFinal(StatusPropostaEnum.RECUSA_BUROCRATICA)
                .evento(EventoEnum.PROPOSTA_CONSISTIDA_COM_CRITICAS)
                .susep("P0029J")
                .build();

        // Criacao do DTO de Proposta de acordo com o status
        Proposta proposta = PropostaFactory.getCorpoMensagem(StatusPropostaEnum.RECUSA_BUROCRATICA);
        proposta.setStatusProposta(StatusPropostaEnum.RECUSA_BUROCRATICA);
        proposta.setNumeroProposta(1L);
        proposta.setNumeroCotacao(1L);
        proposta.setIdDocumentoJson(new CorrelationId());

        // Motivos de Recusa
        List<MotivoRecusa> listaRecusas = new ArrayList<>();
        listaRecusas.add(new MotivoRecusa(1, "Veiculo com restricoes de leilao"));
        proposta.setListaRecusas(listaRecusas);

        Mensagem mensagem = new Mensagem<PropostaRecusadaBurocratica>();
        mensagem.setTrilhaAuditoria(trilhaAuditoria);
        mensagem.setCorpo(proposta);

        assertThat(mensagem.getTrilhaAuditoria()).isNotNull();
        assertThat(mensagem.getCorpo()).isNotNull();
    }

    @Test
    void alteracaoStatusConsistindoParaAceita() throws Exception {

        TrilhaAuditoria trilhaAuditoria = TrilhaAuditoria.builder()
                .numeroCotacao(1L)
                .numeroProposta(1L)
                .codigoMarca(1)
                .codigoSegmento(1)
                .idDocumentoJson(new CorrelationId())
                .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                .dataCriacao(OffsetDateTime.now())
                .propostaStatusInicial(StatusPropostaEnum.CONSISTINDO)
                .propostaStatusFinal(StatusPropostaEnum.ACEITA)
                .evento(EventoEnum.PROPOSTA_CONSISTIDA_COM_CRITICAS)
                .susep("P0029J")
                .build();

        // Criacao do DTO de Proposta de acordo com o status
        Proposta proposta = PropostaFactory.getCorpoMensagem(StatusPropostaEnum.ACEITA);
        proposta.setStatusProposta(StatusPropostaEnum.ACEITA);
        proposta.setNumeroProposta(1L);
        proposta.setNumeroCotacao(1L);
        proposta.setIdDocumentoJson(new CorrelationId());

        // Motivos de Recusa
        List<MotivoRecusa> listaRecusas = new ArrayList<>();
        listaRecusas.add(new MotivoRecusa(1, "Veiculo com restricoes de leilao"));

        // Proposta aceita, nao pode conter lista de recusas
        Exception thrown = assertThrows(
                Exception.class,
                () -> proposta.setListaRecusas(listaRecusas),
                "");

        assertFalse(thrown.getMessage().contains("ok"));
    }

    @Test
    void alteracaoStatusRecebidaParaAceita() throws Exception {

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> TrilhaAuditoria.builder()
                        .numeroCotacao(1L)
                        .numeroProposta(1L)
                        .codigoMarca(1)
                        .codigoSegmento(1)
                        .idDocumentoJson(new CorrelationId())
                        .emissor(AplicacaoEnum.SBOOT_AUTO_ECOR_PROPOSTA)
                        .dataCriacao(OffsetDateTime.now())
                        .propostaStatusInicial(StatusPropostaEnum.RECEBIDA)
                        .propostaStatusFinal(StatusPropostaEnum.ACEITA)
                        .evento(EventoEnum.PROPOSTA_ENVIADA_CONSISTENCIA)
                        .susep("P0029J")
                        .build(),
                "");

        assertFalse(thrown.getMessage().contains("ok"));

    }
}
