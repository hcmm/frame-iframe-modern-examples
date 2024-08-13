package com.jetnuvem.email.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.jetnuvem.email.api.controller.EmailMessageController;
import com.jetnuvem.email.api.model.EmailMessageModel;
import com.jetnuvem.email.domain.model.EmailMessage;

@Component
public class EmailMessageModelAssembler 
		extends RepresentationModelAssemblerSupport<EmailMessage, EmailMessageModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	public EmailMessageModelAssembler() {
		super(EmailMessageController.class, EmailMessageModel.class);
	}
	
	@Override
	public EmailMessageModel toModel(EmailMessage email) {
		EmailMessageModel emailModel = createModelWithId(email.getId(), email);
		modelMapper.map(email, emailModel);
		
		
		return emailModel;
	}
	
	
	public Page<EmailMessageModel> toModel(Page<EmailMessage> domainsPage) {
        return domainsPage.map(domain -> modelMapper.map(domain, EmailMessageModel.class));

	}
	
}
