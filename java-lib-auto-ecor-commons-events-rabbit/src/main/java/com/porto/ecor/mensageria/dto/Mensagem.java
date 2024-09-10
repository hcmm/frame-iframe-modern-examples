package com.porto.ecor.mensageria.dto;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Mensagem<T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ToString.Include
    private TrilhaAuditoria trilhaAuditoria;

    private T corpo;

}
