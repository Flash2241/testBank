package com.example.dossier.service;

import com.example.dossier.model.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmailServiceImpl {


    public void sendEmail(EmailMessage emailMessage){
        log.info("Sending email to : {}, wint theme :{}, and statementId:{}",emailMessage.getAddress(), emailMessage.getTheme(),emailMessage.getStatasmentId());

    }
}
