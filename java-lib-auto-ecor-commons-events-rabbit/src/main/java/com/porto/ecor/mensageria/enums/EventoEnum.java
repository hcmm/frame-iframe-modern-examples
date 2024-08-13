package com.porto.ecor.mensageria.enums;

public enum EventoEnum {
    PROPOSTA_RECEBIDA("Proposta formatada"),
    PROPOSTA_ENVIADA_CONSISTENCIA("Proposta enviada para consistência"),
    PROPOSTA_RECEBIDA_CONSISTENCIA("Proposta recebida para consistência"),
    PROPOSTA_CONSISTIDA_SEM_CRITICAS("Proposta consistida e sem críticas"),
    PROPOSTA_CONSISTIDA_COM_CRITICAS("Proposta consistida e com críticas"),
    PROPOSTA_CONSISTIDA_COM_RECUSA("Proposta consistida e com recusa"),
    PROPOSTA_ENVIADA_WORKFLOW("Proposta enviada para o Workflow"),
    PROPOSTA_RECEBIDA_WORKFLOW("Proposta recebida para análise no Workflow"),
    PROPOSTA_ENVIADA_EMISSAO("Proposta enviada para emissão da apólice"),
    PROPOSTA_RECEBIDA_EMISSAO("Proposta recebida para emissão da apólice"),
    PROPOSTA_ENVIADA_FINANCEIRO("Proposta enviada para o financeiro"),
    PROPOSTA_RECEBIDA_FINANCEIRO("Proposta recebida pelo financeiro"),
    APOLICE_EMITIDA("Apólice emitida"),
    PROPOSTA_RECUSADA("Proposta recusada");

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    EventoEnum(String descricao){
        this.descricao = descricao;
    }
}
