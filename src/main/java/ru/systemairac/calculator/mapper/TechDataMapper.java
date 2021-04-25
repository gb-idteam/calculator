package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.dto.TechDataDto;

@Mapper
public interface TechDataMapper {
    TechDataMapper MAPPER = Mappers.getMapper(TechDataMapper.class);

    TechData toTechData(TechDataDto dto);

    @InheritInverseConfiguration
    TechDataDto fromTechData(TechData techData);
}
