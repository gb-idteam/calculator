package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;

import java.util.List;

public interface HumidifierComponentMapper {
    HumidifierComponentMapper MAPPER = Mappers.getMapper(HumidifierComponentMapper.class);

    HumidifierComponent toHumidifierComponent (HumidifierComponentDto dto);
    List<HumidifierComponent> toHumidifierComponentList (List<HumidifierComponentDto> dtos);

    @InheritInverseConfiguration
    HumidifierComponentDto fromHumidifierComponent (HumidifierComponent component);
    List<HumidifierComponentDto> fromHumidifierComponentList (List<HumidifierComponent> components);
}
