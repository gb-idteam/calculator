package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;

import java.util.List;

@Mapper
public interface HumidifierComponentMapper {
    HumidifierComponentMapper MAPPER = Mappers.getMapper(HumidifierComponentMapper.class);

    HumidifierComponent toHumidifierComponent (HumidifierComponentDto humidifierComponent);
    List<HumidifierComponent> toHumidifierComponentList (List<HumidifierComponentDto> humidifierComponents);

    @InheritInverseConfiguration
    HumidifierComponentDto fromHumidifierComponent (HumidifierComponent humidifierComponent);
    List<HumidifierComponentDto> fromHumidifierComponentList (List<HumidifierComponent> humidifierComponents);
}
