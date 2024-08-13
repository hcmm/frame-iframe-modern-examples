package com.jetnuvem.email.api.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jetnuvem.email.api.assembler.EmailMessageInputDisassembler;
import com.jetnuvem.email.api.assembler.EmailMessageModelAssembler;
import com.jetnuvem.email.api.input.EmailMessageInput;
import com.jetnuvem.email.api.model.EmailMessageModel;
import com.jetnuvem.email.domain.model.EmailMessage;
import com.jetnuvem.email.domain.repository.EmailMessageRepository;
import com.jetnuvem.email.domain.service.EmailMessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/email-messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailMessageController {

    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @Autowired
    private EmailMessageService emailMessageService;

    @Autowired
    private EmailMessageModelAssembler emailMessageModelAssembler;

    @Autowired
    private EmailMessageInputDisassembler emailMessageInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<EmailMessage> pagedResourcesAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmailMessageModel create(@RequestBody @Valid EmailMessageInput emailMessageInput) {
        EmailMessage emailMessage = emailMessageInputDisassembler.toDomainObject(emailMessageInput);
        emailMessage = emailMessageService.criarEmailMessage(emailMessage);
        return emailMessageModelAssembler.toModel(emailMessage);
    }

    @PutMapping("/{emailMessageId}")
    public EmailMessageModel update(@PathVariable UUID emailMessageId,
                                    @RequestBody @Valid EmailMessageInput emailMessageInput) {
        EmailMessage emailMessage = emailMessageService.buscarOuFalhar(emailMessageId);
        emailMessageInputDisassembler.copyToDomainObject(emailMessageInput, emailMessage);
        emailMessage = emailMessageService.salvar(emailMessage);
        return emailMessageModelAssembler.toModel(emailMessage);
    }

    @DeleteMapping("/{emailMessageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID emailMessageId) {
        emailMessageService.excluir(emailMessageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{emailMessageId}")
    public EmailMessageModel buscar(@PathVariable UUID emailMessageId) {
        EmailMessage emailMessage = emailMessageService.buscarOuFalhar(emailMessageId);
        return emailMessageModelAssembler.toModel(emailMessage);
    }

    @GetMapping
    public PagedModel<EmailMessageModel> findAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<EmailMessage> emailMessagesPage = emailMessageRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(emailMessagesPage, emailMessageModelAssembler);
    }

    @GetMapping("/search-by-subject")
    public PagedModel<EmailMessageModel> findBySubject(@RequestParam String subject,
                                                       @PageableDefault(size = 10) Pageable pageable) {
        Page<EmailMessage> emailMessagesPage = emailMessageRepository.findBySubjectContainingIgnoreCase(subject, pageable);
        return pagedResourcesAssembler.toModel(emailMessagesPage, emailMessageModelAssembler);
    }
}

