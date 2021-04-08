package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.Estimate;
import ru.systemairac.calculator.dto.EstimateDto;

import java.util.List;

@Mapper(uses = {HumidifierMapper.class,HumidifierComponentMapper.class,VaporDistributorMapper.class})
public interface EstimateMapper {
    EstimateMapper MAPPER = Mappers.getMapper(EstimateMapper.class);

    @Mapping(source = "humidifier", target = "humidifier")
    @Mapping(source = "humidifierComponents", target = "humidifierComponents")
    @Mapping(source = "vaporDistributor", target = "vaporDistributor")
    Estimate toEstimate(EstimateDto dto);
    List<Estimate> toEstimateList(List<EstimateDto> estimates);
    @InheritInverseConfiguration
    EstimateDto fromEstimate(Estimate estimate);
    List<EstimateDto> fromEstimateList(List<Estimate> estimates);
}
