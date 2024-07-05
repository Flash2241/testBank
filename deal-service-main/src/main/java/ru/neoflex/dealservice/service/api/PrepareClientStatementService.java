package ru.neoflex.dealservice.service.api;

import ru.neoflex.dealservice.dto.LoanStatementRequestDto;
import ru.neoflex.dealservice.dal.entity.Statement;

public interface PrepareClientStatementService {
    Statement createStatement(LoanStatementRequestDto loanStatementRequestDto);
}
