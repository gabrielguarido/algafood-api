package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.email.EmailSenderService;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    private final EmailProperties emailProperties;

    @Autowired
    public SmtpEmailSenderServiceImpl(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Message message) {
        try {
            var mimeMessage = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), true);
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setFrom(emailProperties.getSender());

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException(e);
        }
    }
}
