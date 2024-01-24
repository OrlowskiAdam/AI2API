package com.example.ai2api.service;

import com.example.ai2api.dto.EmailRegisterRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface EmailRegisterService {

    @Async
    void sendConfirmationEmail(EmailRegisterRequest emailRegisterRequest);
}
