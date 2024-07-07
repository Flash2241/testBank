package ru.neoflex.dealservice.service.api;

import ru.neoflex.dealservice.dto.FinishRegistrationRequestDto;

import java.util.UUID;

public interface StatementService {
    void calculate(UUID id, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
