package com.jetnuvem.email.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jetnuvem.email.api.input.EmailMessageInput;
import com.jetnuvem.email.domain.model.EmailMessage;

@Component
public class EmailMessageInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public EmailMessage toDomainObject(EmailMessageInput emailInput) {
		return modelMapper.map(emailInput, EmailMessage.class);
	}
	
	public void copyToDomainObject(EmailMessageInput emailInput, EmailMessage email) {
		modelMapper.map(emailInput, email);
	}
	
}
