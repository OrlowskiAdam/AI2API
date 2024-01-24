package com.example.ai2api.service.impl;

import com.example.ai2api.dto.EmailRegisterRequest;
import com.example.ai2api.exception.FileNoExistException;
import com.example.ai2api.properties.EmailRegisterProperties;
import com.example.ai2api.service.EmailRegisterService;
import com.example.ai2api.utils.filestorage.FileStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;

@Service
@EnableConfigurationProperties(EmailRegisterProperties.class)
@RequiredArgsConstructor
@Slf4j
public class EmailRegisterServiceImpl implements EmailRegisterService {

    private final EmailRegisterProperties emailRegisterProperties;
    private final JavaMailSender javaMailSender;
    private final FileStorage fileStorage;

    @Async
    public void sendConfirmationEmail(EmailRegisterRequest emailRegisterRequest) {

        final var subject = emailRegisterProperties.getSubject();
        final var email = emailRegisterRequest.email();

        javaMailSender.send((mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo(email);
            message.setSubject(subject);
            log.info("Sending confirmation email register {} with subject {}", email, subject);
            message.setText(loadTextMessage(emailRegisterRequest), true);
            message.addInline("image", loadImage());
        }
        ));
    }

    private ByteArrayDataSource loadImage() throws IOException {
        final var filepath = "src/main/resources/files/email/email-register.png";
        return new ByteArrayDataSource(fileStorage.getFileBytes(filepath), fileStorage.getFileType(filepath));
    }

    private String loadTextMessage(EmailRegisterRequest emailRegisterRequest) {
        return getMessageFromFile(emailRegisterRequest.email(), emailRegisterRequest.login());
    }

    private String getMessageFromFile(
            String email,
            String login) {
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(getFile())) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
        }
        String message = stringBuilder.toString();

        return String.format(message, login, LocalDate.now(), email, login);
    }

    private InputStream getFile() {
        final var pathToMessage = emailRegisterProperties.getPathToMessage();
        InputStream resource = getClass().getResourceAsStream(pathToMessage);
        log.info("Getting email template file from  properties: {}", pathToMessage);
        if (resource == null)
            throw new FileNoExistException();
        return resource;
    }
}
