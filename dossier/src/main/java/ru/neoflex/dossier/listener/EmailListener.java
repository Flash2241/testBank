package ru.neoflex.dossier.listener;

import com.example.dossier.model.EmailMessage;
import com.example.dossier.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailListener {

    private final EmailService emailService;

    @KafkaListener(topics = "finish-registration", groupId = "dossier-group")
    public void listenFinishRegistration(EmailMessage message) {
        log.info("Received Message in group 'dossier-group': {}", message);
        emailService.sendEmail(message);
    }

    @KafkaListener(topics = "create-documents", groupId = "dossier-group")
    public void listenCreateDocuments(EmailMessage message) {
        log.info("Received Message in group 'dossier-group': {}", message);
        emailService.sendEmail(message);
    }

    @KafkaListener(topics = "send-documents", groupId = "dossier-group")
    public void listenSendDocuments(EmailMessage message) {
        log.info("Received Message in group 'dossier-group': {}", message);
        emailService.sendEmail(message);
    }

    @KafkaListener(topics = "send-ses", groupId = "dossier-group")
    public void listenSendSes(EmailMessage message) {
        log.info("Received Message in group 'dossier-group': {}", message);
        emailService.sendEmail(message);
    }

    @KafkaListener(topics = "credit-issued", groupId = "dossier-group")
    public void listenCreditIssued(EmailMessage message) {
        log.info("Received Message in group 'dossier-group': {}", message);
        emailService.sendEmail(message);
    }

    @KafkaListener(topics = "statement-denied", groupId = "dossier-group")
    public void listenStatementDenied(EmailMessage message) {
        log.info("Received Message in group 'dossier-group': {}", message);
        emailService.sendEmail(message);
    }
}
