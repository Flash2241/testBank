package ru.neoflex.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.gateway.dto.*;
import ru.neoflex.gateway.service.DealClient;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private DealClient dealClient;

    // Admin: Get statement by ID
    @GetMapping("/admin/statement/{statementId}")
    public ResponseEntity<StatementDto> getStatementById(@PathVariable UUID statementId) {
        logger.info("Received request to get statement by ID: {}", statementId);
        return dealClient.getStatementById(statementId);
    }

    // Admin: Get all statements
    @GetMapping("/admin/statement")
    public ResponseEntity<List<StatementDto>> getAllStatements() {
        logger.info("Received request to get all statements");
        return dealClient.getAllStatements();
    }

    // User: Create loan statement
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> createLoanStatement(@RequestBody LoanStatementRequestDto requestDto) {
        logger.info("Received request to create loan statement: {}", requestDto);
        return dealClient.processLoanStatement(requestDto);
    }

    // User: Select loan offer
    @PostMapping("/statement/offer")
    public ResponseEntity<Void> selectLoanOffer(@RequestBody LoanOfferDto loanOfferDto) {
        logger.info("Received request to select loan offer: {}", loanOfferDto);
        return dealClient.processOfferSelect(loanOfferDto);
    }

    // User: Calculate credit for statement
    @PostMapping("/statement/calculate/{statementId}")
    public ResponseEntity<Void> calculateCredit(@PathVariable UUID statementId, @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        logger.info("Received request to calculate credit for statement ID: {}, with data: {}", statementId, finishRegistrationRequestDto);
        return dealClient.calculateCredit(statementId, finishRegistrationRequestDto);
    }
}
