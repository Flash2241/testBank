package ru.neoflex.dealservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.neoflex.dealservice.dal.entity.Client;
import ru.neoflex.dealservice.dto.LoanStatementRequestDto;


@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    Client toEntity(LoanStatementRequestDto dto);


}
