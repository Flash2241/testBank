package com.example.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "deal-service", url = "http://localhost:8081")
public interface DealClient {

    @PostMapping("/deal/statement")
    ResponseEntity<List<LoanOfferDto>> createStatement(LoanStatementRequestDto request);

    @PostMapping("/deal/offer/select")
    ResponseEntity<Void> selectOffer(LoanOfferDto offer);

    @PostMapping("/deal/calculate/{statementId}")
    ResponseEntity<Void> calculateCredit(@PathVariable String statementId, @RequestBody FinishRegistrationRequestDto request);

    @GetMapping("/deal/admin/statement/{statementId}")
    ResponseEntity<StatementDto> getStatementById(@PathVariable String statementId);

    @GetMapping("/deal/admin/statement")
    ResponseEntity<List<StatementDto>> getAllStatements();
}
