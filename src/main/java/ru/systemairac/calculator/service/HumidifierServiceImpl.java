package ru.systemairac.calculator.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierMapper;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.repository.humidifier.HumidifierFilter;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierSpecification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    private static final int NUMBER_OF_RESULTS = 3;

    private final HumidifierRepository humidifierRepository;

    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;

    public HumidifierServiceImpl(HumidifierRepository humidifierRepository) {
        this.humidifierRepository = humidifierRepository;
        init();
    }

    private Humidifier generateHumidifier(String articul,double elPower,double capacity, EnumHumidifierType type, int phase, int vaporPipeDiameter,int numberOfCylinders,int voltage, BigDecimal price) {
        Humidifier humidifier =Humidifier.builder()
                .articleNumber(articul)
                .electricPower(elPower)
                .capacity(capacity)
                .humidifierType(type)
                .phase(phase)
                .vaporPipeDiameter(vaporPipeDiameter)
                .numberOfCylinders(numberOfCylinders)
                .voltage(voltage)
                .price(price)
                .build();
        return humidifier;
    }

    public void init(){
        EnumHumidifierType type1 = EnumHumidifierType.ELECTRODE;
        EnumHumidifierType type2 = EnumHumidifierType.HEATING_ELEMENT;
        List<Humidifier> humidifiers = new ArrayList<>();
        humidifiers.add(generateHumidifier("123",7,25,type1,3,25,
                1,380,new BigDecimal(1500)));
        humidifiers.add(generateHumidifier("1234",5,29,type2,1,25,
                1,220,new BigDecimal(1000)));
        humidifiers.add(generateHumidifier("12",7,50,type1,1,25,
                1,380,new BigDecimal(1500)));
        humidifiers.add(generateHumidifier("1",5,15,type2,3,25,
                1,220,new BigDecimal(1000)));
        humidifiers.add(generateHumidifier("2",20,15,type1,1,25,
                1,220,new BigDecimal(1000)));
        humidifiers.add(generateHumidifier("3",50,15,type2,1,25,
                1,220,new BigDecimal(1000)));
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
        return mapper.fromHumidifierList(humidifiers);
    }

    @Override
    public HumidifierDto findById(Long id) {
        return mapper.fromHumidifier(humidifierRepository.findById(id).orElse(null));
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
    public void saveAll(List<Humidifier> humidifier) {
        humidifierRepository.saveAll(humidifier);
    }

    @Override
    public void deleteById(Long id) {
    }
}
