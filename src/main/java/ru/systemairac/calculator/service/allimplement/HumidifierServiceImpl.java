package ru.systemairac.calculator.service.allimplement;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.exception.HumidifierNotFoundException;
import ru.systemairac.calculator.mapper.HumidifierMapper;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;
import ru.systemairac.calculator.repository.HumidifierComponentRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierFilter;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierSpecification;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    private static final int NUMBER_OF_RESULTS = 3;
    private final HumidifierRepository humidifierRepository;

    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;
    private final HumidifierComponentRepository humidifierComponentRepository;

    public HumidifierServiceImpl(HumidifierRepository humidifierRepository, HumidifierComponentRepository humidifierComponentRepository) {
        this.humidifierRepository = humidifierRepository;
        this.humidifierComponentRepository = humidifierComponentRepository;
        init();
    }

    private Humidifier generateHumidifier(Long id, String article,double elPower,double capacity, EnumHumidifierType type, EnumVoltageType voltage, int vaporPipeDiameter,int numberOfCylinders, BigDecimal price) {
        Humidifier humidifier = Humidifier.builder()
                .id(id)
                .articleNumber(article)
                .electricPower(elPower)
                .capacity(capacity)
                .humidifierType(type)
                .voltage(voltage)
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
        humidifiers.add(generateHumidifier(1L,"123",7,25,type1,EnumVoltageType.THREE_PHASE_380V,25,
                1,new BigDecimal(1500)));
        humidifiers.add(generateHumidifier(2L,"1234",5,29,type2,EnumVoltageType.ONE_PHASE_220V,25,
                1,new BigDecimal(1000)));
        humidifiers.add(generateHumidifier(3L,"12",7,50,type1,EnumVoltageType.THREE_PHASE_380V,25,
                1,new BigDecimal(1500)));
        humidifiers.add(generateHumidifier(4L,"1",5,15,type2,EnumVoltageType.THREE_PHASE_380V,25,
                1,new BigDecimal(1000)));
        humidifiers.add(generateHumidifier(5L,"2",20,15,type1,EnumVoltageType.ONE_PHASE_220V,25,
                1,new BigDecimal(1000)));
        humidifiers.add(generateHumidifier(6L,"3",50,15,type2,EnumVoltageType.ONE_PHASE_220V,25,
                1,new BigDecimal(1000)));
        humidifierRepository.saveAll(humidifiers);
    }
    @Override
    public List<Humidifier> findHumidifiers(Double minimalCapacity, EnumVoltageType voltage, EnumHumidifierType type) {
        return humidifierRepository.findAll(
                new HumidifierSpecification(new HumidifierFilter(minimalCapacity, voltage, type)),
                PageRequest.of(0, NUMBER_OF_RESULTS, Sort.by(Sort.Order.asc("capacity")))
        ).toList();
    }

    @Override
    public List<HumidifierDto> findDtoHumidifiers(Double minimalCapacity, EnumVoltageType voltage, EnumHumidifierType type) {
        List<Humidifier> humidifiers = humidifierRepository.findAll(
                new HumidifierSpecification(new HumidifierFilter(minimalCapacity, voltage, type)),
                PageRequest.of(0,NUMBER_OF_RESULTS, Sort.by(Sort.Order.asc("capacity")))
        ).toList();
        return mapper.fromHumidifierList(humidifiers);
    }

    @Override
    public Humidifier findHumidifierById(Long id) {
        return humidifierRepository.findById(id).orElseThrow(
                () -> new HumidifierNotFoundException("Humidifier with id " + id + " doesn't exist!"));
    }

    @Override
    public HashMap<Long, Integer> getAllDiameters(List<Humidifier> humidifierList) {
        HashMap<Long , Integer> diametersMap = new HashMap<>();
        humidifierList.forEach(humidifier -> diametersMap.put(humidifier.getId(),humidifier.getVaporPipeDiameter()));
        return diametersMap;
    }

    @Override
    public List<Humidifier> findHumidifiersByIds(List<Long> ids) {
        return humidifierRepository.findAllById(ids);
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

    public boolean existsById(Long id) {
        return humidifierRepository.existsById(id);
    }

    public Humidifier saveOrUpdate(Humidifier humidifier) {
        return humidifierRepository.save(humidifier);
    }
}
