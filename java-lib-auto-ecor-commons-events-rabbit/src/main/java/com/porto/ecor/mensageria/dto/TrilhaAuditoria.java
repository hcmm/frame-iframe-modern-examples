package com.porto.ecor.mensageria.dto;

import com.porto.ecor.mensageria.enums.AplicacaoEnum;
import com.porto.ecor.mensageria.enums.EventoEnum;
import com.porto.ecor.mensageria.enums.StatusPropostaEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Jacksonized @Builder
@ToString(onlyExplicitlyIncluded = true)
public class TrilhaAuditoria implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NonNull
    @ToString.Include
    private AplicacaoEnum emissor;

    @NonNull
    @ToString.Include
    private OffsetDateTime dataCriacao;

    @NonNull
    @ToString.Include
    private Long numeroProposta;

    @NonNull
    @ToString.Include
    private StatusPropostaEnum propostaStatusInicial;

    @NonNull
    @ToString.Include
    private StatusPropostaEnum propostaStatusFinal;

    @NonNull
    @ToString.Include
    @Setter
    private CorrelationId idDocumentoJson;

    @NonNull
    @ToString.Include
    private Long numeroCotacao;

    @ToString.Include
    private Integer codigoMarca;

    @ToString.Include
    private Integer codigoSegmento;

    @ToString.Include
    private Long numeroApolice;

    @NonNull
    @ToString.Include
    private EventoEnum evento;

    @NonNull
    @ToString.Include
    private String susep;
    
    public static class TrilhaAuditoriaBuilder {
    	private StatusPropostaEnum propostaStatusInicial;
    	private StatusPropostaEnum propostaStatusFinal;

    	public TrilhaAuditoriaBuilder propostaStatusInicial(@NonNull StatusPropostaEnum propostaStatusInicial) {
	      if (propostaStatusInicial == null)
	        throw new NullPointerException("propostaStatusInicial is marked non-null but is null"); 
	      this.propostaStatusInicial = propostaStatusInicial;
	      verificaStatusInicialEFinal();
	      return this;
	    }
	    
	    public TrilhaAuditoriaBuilder propostaStatusFinal(@NonNull StatusPropostaEnum propostaStatusFinal) {
	      if (propostaStatusFinal == null)
	        throw new NullPointerException("propostaStatusFinal is marked non-null but is null"); 
	      this.propostaStatusFinal = propostaStatusFinal;
	      verificaStatusInicialEFinal();
	      return this;
	    }
	    
	    private void verificaStatusInicialEFinal() {
	      if(propostaStatusInicial != null && propostaStatusFinal != null) {
	    	  if (!StatusPropostaEnum.obterProximosStatus(this.propostaStatusInicial.getCodigo()).contains(this.propostaStatusFinal))
	    		  throw new IllegalStateException("Status final nao permitido para o status inicial informado."); 
	      }	
	    }
	}

}
