package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;

import java.util.List;

@Mapper
public interface HumidifierMapper {
    HumidifierMapper MAPPER = Mappers.getMapper(HumidifierMapper.class);

    Humidifier toHumidifier(HumidifierDto humidifier);
    List<Humidifier> toHumidifierList(List<HumidifierDto> humidifiers);
    @InheritInverseConfiguration
    HumidifierDto fromHumidifier(Humidifier humidifier);
    List<HumidifierDto> fromHumidifierList(List<Humidifier> humidifiers);
}
