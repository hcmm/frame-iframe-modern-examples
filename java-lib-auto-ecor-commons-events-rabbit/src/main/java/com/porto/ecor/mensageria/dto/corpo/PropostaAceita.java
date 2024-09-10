package com.porto.ecor.mensageria.dto.corpo;

import java.util.List;

public class PropostaAceita extends Proposta {

    @Override
    public List<MotivoRecusa> getListaRecusas() throws Exception {
        throw new Exception("Proposta aceita, não possui recusas");
    }

    @Override
    public void setListaRecusas(List<MotivoRecusa> listaRecusas) throws Exception {
        throw new Exception("Proposta aceita, não possui recusas");
    }
}
