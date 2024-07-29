package com.example.gateway.controller;

import com.example.gateway.service.DealClient;
import com.example.gateway.dto.LoanOfferDto;
import com.example.gateway.dto.LoanStatementRequestDto;
import com.example.gateway.dto.FinishRegistrationRequestDto;
import com.example.gateway.dto.StatementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private DealClient dealClient;

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> createStatement(@RequestBody LoanStatementRequestDto request) {
        logger.info("Received request to create statement: {}", request);
        ResponseEntity<List<LoanOfferDto>> response = dealClient.createStatement(request);
        logger.info("Response from deal service: {}", response.getBody());
        return response;
    }

    @PostMapping("/statement/offer")
    public ResponseEntity<Void> selectOffer(@RequestBody LoanOfferDto offer) {
        logger.info("Received request to select offer: {}", offer);
        ResponseEntity<Void> response = dealClient.selectOffer(offer);
        logger.info("Response from deal service: {}", response.getStatusCode());
        return response;
    }

    @PostMapping("/statement/calculate/{statementId}")
    public ResponseEntity<Void> calculateCredit(@PathVariable String statementId, @RequestBody FinishRegistrationRequestDto request) {
        logger.info("Received request to calculate credit for statementId: {}, with data: {}", statementId, request);
        ResponseEntity<Void> response = dealClient.calculateCredit(statementId, request);
        logger.info("Response from deal service: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/admin/statement/{statementId}")
    public ResponseEntity<StatementDto> getStatementById(@PathVariable String statementId) {
        logger.info("Received request to get statement by id: {}", statementId);
        ResponseEntity<StatementDto> response = dealClient.getStatementById(statementId);
        logger.info("Response from deal service: {}", response.getBody());
        return response;
    }

    @GetMapping("/admin/statement")
    public ResponseEntity<List<StatementDto>> getAllStatements() {
        logger.info("Received request to get all statements");
        ResponseEntity<List<StatementDto>> response = dealClient.getAllStatements();
        logger.info("Response from deal service: {}", response.getBody());
        return response;
    }
}
