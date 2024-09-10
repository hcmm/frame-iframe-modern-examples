package com.porto.ecor.mensageria.dto.corpo;

public class MotivoRecusa {

    public MotivoRecusa(Integer idRecusa, String observacaoRecusa) {
        this.idRecusa = idRecusa;
        this.observacaoRecusa = observacaoRecusa;
    }

    private Integer idRecusa;
    private String observacaoRecusa;

    public Integer getIdRecusa() {
        return idRecusa;
    }

    public void setIdRecusa(Integer idRecusa) {
        this.idRecusa = idRecusa;
    }

    public String getObservacaoRecusa() {
        return observacaoRecusa;
    }

    public void setObservacaoRecusa(String observacaoRecusa) {
        this.observacaoRecusa = observacaoRecusa;
    }
}
