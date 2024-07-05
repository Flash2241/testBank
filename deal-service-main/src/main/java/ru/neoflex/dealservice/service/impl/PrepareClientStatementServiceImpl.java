package ru.neoflex.dealservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.dealservice.dto.LoanStatementRequestDto;
import ru.neoflex.dealservice.mapper.ClientMapper;
import ru.neoflex.dealservice.model.Client;
import ru.neoflex.dealservice.dal.entity.Statement;
import ru.neoflex.dealservice.dal.repository.ClientRepository;
import ru.neoflex.dealservice.dal.repository.StatementRepository;
import ru.neoflex.dealservice.service.api.PrepareClientStatementService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrepareClientStatementServiceImpl implements PrepareClientStatementService {

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Override
    @Transactional
    public Statement createStatement(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Processing loan statement request: {}", loanStatementRequestDto);
        Client client = clientMapper.toEntity(loanStatementRequestDto);
        log.info("Mapped client entity: {}", client);
        client = clientRepository.save(client);
        log.info("Saved client entity: {}", client);

        Statement statement = new Statement(client);
        statement = statementRepository.save(statement);
        log.info("Saved statement entity: {}", statement);
        return statement;
    }
}
