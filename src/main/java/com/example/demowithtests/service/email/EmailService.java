package com.example.demowithtests.service.email;

public interface EmailService {
    void sendMessage(String to, String subject, String text);
}
