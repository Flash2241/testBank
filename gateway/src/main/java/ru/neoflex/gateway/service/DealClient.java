package ru.neoflex.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.gateway.dto.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "deal-service", url = "${deal.service.url}")
public interface DealClient {

    @GetMapping("/deal/admin/statement/{statementId}")
    ResponseEntity<StatementDto> getStatementById(@PathVariable UUID statementId);

    @GetMapping("/deal/admin/statement")
    ResponseEntity<List<StatementDto>> getAllStatements();

    @PostMapping("/deal/statement")
    ResponseEntity<List<LoanOfferDto>> processLoanStatement(@RequestBody LoanStatementRequestDto dto);

    @PostMapping("/deal/statement/offer")
    ResponseEntity<Void> processOfferSelect(@RequestBody LoanOfferDto loanOfferDto);

    @PostMapping("/deal/statement/calculate/{statementId}")
    ResponseEntity<Void> calculateCredit(@PathVariable UUID statementId, @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto);
}
