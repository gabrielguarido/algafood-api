package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.email.EmailSenderService;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import freemarker.template.Configuration;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@NoArgsConstructor
public class SmtpEmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration configuration;

    @Override
    public void send(Message message) {
        try {
            var mimeMessage = createMimeMessage(message);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException(e);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        var body = processHtmlTemplate(message);

        var mimeMessage = mailSender.createMimeMessage();

        var helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setSubject(message.getSubject());
        helper.setText(body, true);
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setFrom(emailProperties.getSender());
        return mimeMessage;
    }

    private String processHtmlTemplate(Message message) {
        try {
            var template = configuration.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException(e);
        }
    }
}
