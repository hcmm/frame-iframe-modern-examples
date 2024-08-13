package com.jetnuvem.email.domain.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "email_message")
public class EmailMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "O endereço de e-mail deve ser válido")
    @NotEmpty(message = "O destinatário não pode estar vazio")
    private String toAddress;

    @NotEmpty(message = "O assunto não pode estar vazio")
    private String subject;

    @NotEmpty(message = "O corpo do e-mail não pode estar vazio")
    @Size(min = 10, message = "O corpo do e-mail deve ter pelo menos 10 caracteres")
    private String text;
    
    @NotEmpty(message = "O tipo não pode estar vazio")
    private String tipo;
    

}
