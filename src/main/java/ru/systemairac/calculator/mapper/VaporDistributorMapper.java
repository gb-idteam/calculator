package ru.systemairac.calculator.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.VaporDistributorDto;

import java.util.List;

@Mapper
public interface VaporDistributorMapper {
    VaporDistributorMapper MAPPER = Mappers.getMapper(VaporDistributorMapper.class);

    VaporDistributor toVaporDistributor (VaporDistributorDto dto);
    List<VaporDistributor> toVaporDistributorList (List<VaporDistributorDto> dtos);

    @InheritInverseConfiguration
    VaporDistributorDto fromVaporDistributor (VaporDistributor distributor);
    List<VaporDistributorDto> fromVaporDistributorList (List<VaporDistributor> distributors);
}
