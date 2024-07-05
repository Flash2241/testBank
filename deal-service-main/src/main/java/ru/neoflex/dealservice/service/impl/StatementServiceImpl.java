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
import ru.neoflex.dealservice.dto.*;
import ru.neoflex.dealservice.dto.calculator.CreditDto;
import ru.neoflex.dealservice.dto.calculator.ScoringDataDto;
import ru.neoflex.dealservice.mapper.ClientToScoringDataMapper;
import ru.neoflex.dealservice.mapper.CreditMapper;
import ru.neoflex.dealservice.dal.entity.Credit;
import ru.neoflex.dealservice.dal.entity.Statement;
import ru.neoflex.dealservice.dal.repository.CreditRepository;
import ru.neoflex.dealservice.dal.repository.StatementRepository;
import ru.neoflex.dealservice.service.api.StatementService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementServiceImpl implements StatementService {

    @Value("${calculator-service.url}")
    private String calculatorServiceUrl;

    private final StatementRepository statementRepository;
    private final CreditRepository creditRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ClientToScoringDataMapper statementMapper = ClientToScoringDataMapper.INSTANCE;
    private final CreditMapper creditMapper = CreditMapper.INSTANCE;

    @Override
    public void calculate(UUID id, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        log.info("Calculating credit for statement ID: {} with registration data: {}", id, finishRegistrationRequestDto);
        Statement statement = statementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Statement not found"));
        log.info("Found statement entity: {}", statement);

        ScoringDataDto scoringDataDto = statementMapper.toScoringDataDto(statement.getClient(), finishRegistrationRequestDto);
        log.info("Mapped scoring data DTO: {}", scoringDataDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<ScoringDataDto> request = new HttpEntity<>(scoringDataDto, headers);
        log.info("Sending scoring data DTO to calculator service: {}", scoringDataDto);
        ResponseEntity<CreditDto> response = restTemplate.exchange(
                calculatorServiceUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        CreditDto creditDto = response.getBody();
        log.info("Received credit DTO from calculator service: {}", creditDto);
        Credit credit = creditMapper.creditDtoToCredit(creditDto);
        creditRepository.save(credit);
        log.info("Saved credit entity: {}", credit);

        statementRepository.save(statement);
        log.info("Saved statement entity: {}", statement);
    }
}
