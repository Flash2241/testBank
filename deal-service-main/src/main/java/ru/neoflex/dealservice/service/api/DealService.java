package ru.neoflex.dealservice.service.api;

import ru.neoflex.dealservice.dto.LoanOfferDto;
import ru.neoflex.dealservice.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> processLoanStatement(LoanStatementRequestDto dto);

    void processOfferSelect(LoanOfferDto loanOfferDto);
}
