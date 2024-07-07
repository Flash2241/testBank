package ru.neoflex.dealservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.dealservice.dto.FinishRegistrationRequestDto;
import ru.neoflex.dealservice.dto.LoanOfferDto;
import ru.neoflex.dealservice.dto.LoanStatementRequestDto;
import ru.neoflex.dealservice.service.api.DealService;
import ru.neoflex.dealservice.service.api.StatementService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Slf4j
public class DealController {

    private final DealService dealService;
    private final StatementService statementService;

    @Operation(summary = "Process Loan Statement", description = "Processes a loan statement request and returns a list of loan offers.")
    @PostMapping("/statement")
    public List<LoanOfferDto> statement(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Received statement request: {}", loanStatementRequestDto);
        List<LoanOfferDto> response = dealService.processLoanStatement(loanStatementRequestDto);
        log.info("Statement response: {}", response);
        return response;
    }

    @Operation(summary = "Select Loan Offer", description = "Processes the selection of a loan offer.")
    @PostMapping("/offer/select")
    public void offerSelect(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("Received offer select request: {}", loanOfferDto);
        dealService.processOfferSelect(loanOfferDto);
        log.info("Offer select processed.");
    }

    @Operation(summary = "Calculate Credit", description = "Calculates the credit based on the given ID and registration data.")
    @PostMapping("/calculate/{id}")
    public void calculate(@PathVariable UUID id, @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("Received calculate request for ID: {}, with data: {}", id, finishRegistrationRequestDto);
        statementService.calculate(id, finishRegistrationRequestDto);
        log.info("Calculate processed for ID: {}", id);
    }
}
