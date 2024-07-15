package com.example.dossier.service;

import com.example.dossier.model.EmailMessage;

public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
