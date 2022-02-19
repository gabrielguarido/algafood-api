package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.email.EmailSenderService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEmailSenderServiceImpl;
import com.algaworks.algafood.infrastructure.service.email.SmtpEmailSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;

    @Autowired
    public EmailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public EmailSenderService emailSenderService() {
        switch (emailProperties.getImpl()) {
            case SMTP:
                return new SmtpEmailSenderServiceImpl();
            case SANDBOX:
                return new SandboxEmailSenderServiceImpl();
            default:
                return null;
        }
    }
}
