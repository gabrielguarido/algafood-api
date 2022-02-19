package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    private Implementation impl = Implementation.SANDBOX;

    @NotNull
    private String sender;

    private Sandbox sandbox = new Sandbox();

    public enum Implementation {
        SMTP, SANDBOX
    }

    @Getter
    @Setter
    public static class Sandbox {

        private String recipient;
    }
}
