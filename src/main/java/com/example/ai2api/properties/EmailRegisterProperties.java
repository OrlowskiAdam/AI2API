package com.example.ai2api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail.register")
@Data
public class EmailRegisterProperties {
    private String subject;
    private String pathToMessage;
}
