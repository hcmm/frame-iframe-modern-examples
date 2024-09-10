package com.porto.ecor.mensageria.enums;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum StatusPropostaEnum {

    EMITIDA(13,Collections.emptyList()),
    RECEBIDO_FINANCEIRO(12, Arrays.asList(StatusPropostaEnum.EMITIDA/*, StatusPropostaEnum.PENDENTE_FINANCEIRO*/)),
    ENVIADO_FINANCEIRO(11, Arrays.asList(StatusPropostaEnum.RECEBIDO_FINANCEIRO)),
    EMITINDO(10, Arrays.asList(StatusPropostaEnum.ENVIADO_FINANCEIRO)),
    A_EMITIR(9, Arrays.asList(StatusPropostaEnum.EMITINDO)),
    ACEITA(4, Arrays.asList(StatusPropostaEnum.A_EMITIR)),
    RECUSADA(14,Collections.emptyList()),
    RECEBIDO_WORKFLOW(8, Arrays.asList(StatusPropostaEnum.RECUSADA, StatusPropostaEnum.ACEITA)),
    ENVIADO_WORKFLOW(7, Arrays.asList(StatusPropostaEnum.RECEBIDO_WORKFLOW)),
    PENDENTE(5, Arrays.asList(StatusPropostaEnum.ENVIADO_WORKFLOW)),
    RECUSA_BUROCRATICA(6, Arrays.asList(StatusPropostaEnum.ENVIADO_WORKFLOW)),
    CONSISTINDO(3, Arrays.asList(StatusPropostaEnum.RECUSA_BUROCRATICA, StatusPropostaEnum.PENDENTE, StatusPropostaEnum.ACEITA)),
    A_CONSISTIR(2, Arrays.asList(StatusPropostaEnum.CONSISTINDO)),
    RECEBIDA(1, Arrays.asList(StatusPropostaEnum.A_CONSISTIR)),
    INICIAL(0, Arrays.asList(StatusPropostaEnum.RECEBIDA));

    StatusPropostaEnum(Integer codigo, List<StatusPropostaEnum> proximosStatus) {
        this.codigo = codigo;
        this.proximosStatus = proximosStatus;
    }

    private Integer codigo;
    private List<StatusPropostaEnum> proximosStatus;

    public Integer getCodigo() {
        return codigo;
    }

    private static final Set<StatusPropostaEnum> statusPropostas = Stream
            .of(StatusPropostaEnum.values())
            .collect(Collectors.toCollection(HashSet::new));

    public static List<StatusPropostaEnum> obterProximosStatus(Integer codigoStatusAtual) throws IllegalArgumentException {

        StatusPropostaEnum status = statusPropostas.stream()
                .filter(stat -> stat.getCodigo().equals(codigoStatusAtual))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        "o codigo: " + codigoStatusAtual + " nao eh um codigo valido"));

        return status.proximosStatus;
    }
}
