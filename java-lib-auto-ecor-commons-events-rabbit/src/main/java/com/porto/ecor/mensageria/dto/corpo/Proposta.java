package com.porto.ecor.mensageria.dto.corpo;

import com.porto.ecor.mensageria.dto.CorrelationId;
import com.porto.ecor.mensageria.enums.StatusPropostaEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Proposta {

    @Getter
    @Setter
    private Long numeroProposta;

    @Getter
    @Setter
    private CorrelationId idDocumentoJson;

    @Getter
    @Setter
    private Long numeroCotacao;

    @Getter
    @Setter
    private StatusPropostaEnum statusProposta;

    private List<MotivoRecusa> listaRecusas;

    public List<MotivoRecusa> getListaRecusas() throws Exception {
        return listaRecusas;
    }

    public void setListaRecusas(List<MotivoRecusa> listaRecusas) throws Exception {
        this.listaRecusas = listaRecusas;
    }
}
