package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.dto.UserDto;

import java.util.List;

@Mapper
public interface HumidifierMapper {
    HumidifierMapper MAPPER = Mappers.getMapper(HumidifierMapper.class);

    Humidifier toHumidifier(HumidifierDto dto);
    List<Humidifier> toHouseList(List<HumidifierDto> houses);
    @InheritInverseConfiguration
    HumidifierDto fromHumidifier(Humidifier humidifier);
    List<HumidifierDto> fromHouseList(List<Humidifier> houses);
}
