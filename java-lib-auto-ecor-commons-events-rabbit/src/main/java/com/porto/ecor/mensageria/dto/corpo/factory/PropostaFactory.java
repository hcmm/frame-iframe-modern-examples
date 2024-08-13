package com.porto.ecor.mensageria.dto.corpo.factory;

import com.porto.ecor.mensageria.dto.corpo.*;
import com.porto.ecor.mensageria.enums.StatusPropostaEnum;

public class PropostaFactory {
    public static Proposta getCorpoMensagem(StatusPropostaEnum status) {

        switch (status) {
            case RECEBIDA:
                return new PropostaRecebida();
            case A_CONSISTIR:
                return new PropostaAConsistir();
            case CONSISTINDO:
                return new PropostaConsistindo();
            case ACEITA:
                return new PropostaAceita();
            case PENDENTE:
                return new PropostaPendente();
            case RECUSA_BUROCRATICA:
                return new PropostaRecusadaBurocratica();
            default:
                return null;
        }
    }
}
