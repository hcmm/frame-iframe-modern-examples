package com.jetnuvem.email.domain.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.jetnuvem.email.domain.model.EmailMessage;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    public void sendThymeleafEmail(String to, String subject, String templateName, Map<String, Object> templateVariables)
            throws MessagingException {

        try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			messageHelper.setTo(to);
			messageHelper.setSubject(subject);

			Context thymeleafContext = new Context();
			thymeleafContext.setVariables(templateVariables);
			String htmlBody = thymeleafTemplateEngine.process(templateName, thymeleafContext);

			messageHelper.setText(htmlBody, true);

			emailSender.send(mimeMessage);
			
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (jakarta.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void send(EmailMessage email, Map<String, Object> templateVariables)
            throws MessagingException {

        try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			messageHelper.setTo(email.getToAddress());
			messageHelper.setSubject(email.getSubject());

			Context thymeleafContext = new Context();
			thymeleafContext.setVariables(templateVariables);
			String htmlBody = thymeleafTemplateEngine.process(email.getTipo(), thymeleafContext);

			messageHelper.setText(htmlBody, true);

			emailSender.send(mimeMessage);
			
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (jakarta.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

