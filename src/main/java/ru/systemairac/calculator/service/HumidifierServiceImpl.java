package ru.systemairac.calculator.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierMapper;
import ru.systemairac.calculator.mapper.UserMapper;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.repository.HumidifierFilter;
import ru.systemairac.calculator.repository.HumidifierRepository;
import ru.systemairac.calculator.repository.HumidifierSpecification;
import ru.systemairac.calculator.repository.HumidifierTypeRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    private static final int NUMBER_OF_RESULTS = 3;

    private final HumidifierRepository humidifierRepository;
    private final HumidifierTypeRepository humidifierRepositoryType;
    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;

    public HumidifierServiceImpl(HumidifierRepository humidifierRepository, HumidifierTypeRepository humidifierRepositoryType) {
        this.humidifierRepository = humidifierRepository;
        this.humidifierRepositoryType = humidifierRepositoryType;
        init();
    }

    public void init(){
        HumidifierType type1 = new HumidifierType(null,EnumHumidifierType.ELECTRODE);
        HumidifierType type2 = new HumidifierType(null,EnumHumidifierType.HEATING_ELEMENT);
        List<Humidifier> humidifiers = Arrays.asList(Humidifier.builder()
                        .id(1L)
                        .articleNumber("123")
                        .electricPower(7)
                        .capacity(25)
                        .humidifierType(type1)
                        .phase(3)
                        .vaporPipeDiameter(25)
                        .numberOfCylinders(1)
                        .voltage(380)
                        .price(BigDecimal.valueOf(1500))
                        .build(),
                Humidifier.builder()
                        .id(2L)
                        .articleNumber("1")
                        .electricPower(5)
                        .humidifierType(type2)
                        .capacity(20)
                        .phase(3)
                        .vaporPipeDiameter(25)
                        .numberOfCylinders(1)
                        .voltage(380)
                        .price(BigDecimal.valueOf(1000))
                        .build());
        humidifierRepository.saveAll(humidifiers);
    }
    @Override
    public List<Humidifier> findHumidifiers(double power, EnumHumidifierType humidifierType, int phase) {
        return humidifierRepository.findAll(
                new HumidifierSpecification(new HumidifierFilter(power, phase, humidifierType)),
                PageRequest.of(0, NUMBER_OF_RESULTS, Sort.by(Sort.Order.asc("capacity")))
        ).toList();
    }

    @Override
    public List<HumidifierDto> findDtoHumidifiers(double power, int phase, EnumHumidifierType humidifierType) {
        List<Humidifier> humidifiers = humidifierRepository.findAll(
                new HumidifierSpecification(new HumidifierFilter(power, phase, humidifierType)),
                PageRequest.of(0,NUMBER_OF_RESULTS, Sort.by(Sort.Order.asc("capacity")))
        ).toList();
        return mapper.fromHouseList(humidifiers);
    }

    @Override
    public HumidifierDto findById(Long id) {
        return HumidifierDto.builder()
                .id(1L)
                .articleNumber("1")
                .electricPower(5)
                .maxVaporOutput(20)
                .phase(3)
                .vaporPipeDiameter(25)
                .numberOfCylinders(1)
                .voltage(380)
                .price(BigDecimal.valueOf(1000))
                .build();
    }

    @Override
    public List<HumidifierDto> getAll() {
        return null;
    }

    @Override
    public void save(HumidifierDto humidifierDto) {
    }

    @Override
    public void save(Humidifier humidifier) {
        humidifierRepository.save(humidifier);
    }

    @Override
    public void save(HumidifierType humidifierType) {
        humidifierRepositoryType.save(humidifierType);
    }

    @Override
    public void saveAll(List<Humidifier> humidifier) {
        humidifierRepository.saveAll(humidifier);
    }

    @Override
    public void deleteById(Long id) {
    }
}
