package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@NoArgsConstructor
public class SandboxEmailSenderServiceImpl extends SmtpEmailSenderServiceImpl {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        var mimeMessage = super.createMimeMessage(message);

        var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getRecipient());

        return mimeMessage;
    }
}
