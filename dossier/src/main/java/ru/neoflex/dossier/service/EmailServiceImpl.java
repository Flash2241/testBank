package ru.neoflex.dossier.service;

import com.example.dossier.model.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        log.info("Sending email to: {}, with theme: {}, and statementId: {}",
                emailMessage.getAddress(), emailMessage.getTheme(), emailMessage.getStatasmentId());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.getAddress());
            message.setSubject(emailMessage.getTheme());
            message.setText("Dear Customer,\n\nPlease find your statement with ID: "
                    + emailMessage.getStatasmentId() + ".\n\nBest regards,\nYour Company");

            emailSender.send(message);
            log.info("Email successfully sent to: {}", emailMessage.getAddress());
        } catch (Exception e) {
            log.error("Failed to send email to: {}. Error: {}", emailMessage.getAddress(), e.getMessage());
        }
    }
}
