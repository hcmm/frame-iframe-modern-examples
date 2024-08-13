package com.jetnuvem.email.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jetnuvem.email.domain.model.EmailMessage;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, UUID> {

	EmailMessage findBySubject(String subject);

	Optional<EmailMessage> findById(UUID id);

    Page<EmailMessage> findBySubjectContainingIgnoreCase(String subject, Pageable pageable);

}
