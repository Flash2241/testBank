package ru.neoflex.dealservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.neoflex.dealservice.dto.LoanOfferDto;
import ru.neoflex.dealservice.dto.LoanStatementRequestDto;
import ru.neoflex.dealservice.dal.entity.ApplicationStatus;
import ru.neoflex.dealservice.dal.entity.Statement;
import ru.neoflex.dealservice.dal.repository.StatementRepository;
import ru.neoflex.dealservice.service.api.DealService;
import ru.neoflex.dealservice.service.api.PrepareClientStatementService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    @Value("${calculator-service.url}")
    private String calculatorServiceUrl;

    private final StatementRepository statementRepository;
    private final PrepareClientStatementService prepareClientStatementService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<LoanOfferDto> processLoanStatement(LoanStatementRequestDto loanStatementRequestDto) {
        Statement statement = prepareClientStatementService.createStatement(loanStatementRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<LoanStatementRequestDto> request = new HttpEntity<>(loanStatementRequestDto, headers);
        ResponseEntity<List<LoanOfferDto>> response = restTemplate.exchange(
                calculatorServiceUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        List<LoanOfferDto> loanOffers = response.getBody();
        log.info("Received loan offers: {}", loanOffers);
        for (LoanOfferDto offer : loanOffers) {
            offer.setStatementId(statement.getId());
        }

        return loanOffers;
    }

    @Override
    public void processOfferSelect(LoanOfferDto loanOfferDto) {
        log.info("Processing offer select request: {}", loanOfferDto);
        Statement statement = statementRepository.findById(loanOfferDto.getStatementId())
                .orElseThrow(() -> new RuntimeException("Statement not found"));
        log.info("Found statement entity: {}", statement);

        statement.setStatus(ApplicationStatus.APPROVED);
        List<ApplicationStatus> statusHistory = statement.getStatusHistory();
        if (statusHistory == null) statusHistory = new ArrayList<>();
        statusHistory.add(ApplicationStatus.APPROVED);
        statement.setStatusHistory(statusHistory);
        List<LoanOfferDto> appliedOffer = statement.getAppliedOffer();
        if (appliedOffer == null) appliedOffer = new ArrayList<>();
        appliedOffer.add(loanOfferDto);
        statement.setAppliedOffer(appliedOffer);

        statementRepository.save(statement);
        log.info("Saved statement entity with updated status and applied offer: {}", statement);
    }
}
