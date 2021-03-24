package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;

public interface HumidifierMapper {

    HumidifierMapper MAPPER = Mappers.getMapper(HumidifierMapper.class);

    @Mapping(source = "projects", target = "projects")
    Humidifier toHumidifier(HumidifierDto dto);

    @InheritInverseConfiguration
    HumidifierDto fromHumidifier(Humidifier humidifier);
}
