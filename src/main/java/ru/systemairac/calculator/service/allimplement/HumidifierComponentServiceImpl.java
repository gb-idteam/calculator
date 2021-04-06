package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierComponentMapper;
import ru.systemairac.calculator.myenum.HumidifierComponentType;
import ru.systemairac.calculator.repository.HumidifierComponentRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HumidifierComponentServiceImpl implements HumidifierComponentService {

    private final HumidifierComponentMapper mapper = HumidifierComponentMapper.MAPPER;
    private final HumidifierComponentRepository humidifierComponentRepository;
    private final HumidifierRepository humidifierRepository;

    public HumidifierComponentServiceImpl(HumidifierComponentRepository humidifierComponentRepository, HumidifierRepository humidifierRepository) {
        this.humidifierComponentRepository = humidifierComponentRepository;
        this.humidifierRepository = humidifierRepository;
        init();
    }

    @Override
    public List<HumidifierComponent> getAllComponent() {
        return humidifierComponentRepository.findAll();
    }

    private HumidifierComponent generateOption(Long id, String article, HumidifierComponentType type, BigDecimal price) {
        HumidifierComponent option = HumidifierComponent.builder()
                .id(id)
                .type(type)
                .articleNumber(article)
                .price(price)
                .build();
        return option;
    }

    public void init() {
        List<HumidifierComponent> options = new ArrayList<>();
        options.add(generateOption(1L, "art1", HumidifierComponentType.CYLINDER_WATER_HEATING_KIT, new BigDecimal(1500)));
        options.add(generateOption(2L, "art2", HumidifierComponentType.CYLINDER, new BigDecimal(879)));
        options.add(generateOption(3L, "art3", HumidifierComponentType.CONDENSATE_OUTLET_TUBE_8, new BigDecimal(123)));
        options.add(generateOption(4L, "art4", HumidifierComponentType.FAN_DISTRIBUTOR, new BigDecimal(345)));
        options.add(generateOption(5L, "art5", HumidifierComponentType.LEAK_SENSOR, new BigDecimal(68)));
        options.add(generateOption(6L, "art6", HumidifierComponentType.DUCT_LIMIT_HYGROSTAT, new BigDecimal(50)));
        options.add(generateOption(7L, "art7", HumidifierComponentType.OTHER_DIAMETER_ADAPTER, new BigDecimal(100)));
        options.add(generateOption(8L, "art8", HumidifierComponentType.LED_DISPLAY, new BigDecimal(2000)));
        humidifierComponentRepository.saveAll(options);
    }

    @Override
    public HashMap<Long, List<HumidifierComponentDto>> getAllComponentByHumidifiers(List<HumidifierDto> humidifiers) {
        List<Long> ids = humidifiers.stream().map(HumidifierDto::getId).collect(Collectors.toList());
        List<Humidifier> humidifierList = humidifierRepository.findAllById(ids);
        HashMap<Long , List<HumidifierComponentDto>> componentMap = new HashMap<>();
        humidifierList.forEach(humidifier -> componentMap.put(humidifier.getId(), mapper.fromHumidifierComponentList(humidifier.getHumidifierComponents())));
        return componentMap;
    }

    @Override
    public HashMap<Long, List<HumidifierComponentDto>> findByHumidifier(HumidifierDto humidifierDto) {
        Humidifier humidifier = humidifierRepository.findHumidifierById(humidifierDto.getId());
        HashMap<Long, List<HumidifierComponentDto>> componentMap = new HashMap<>();
        if(humidifier != null){
            componentMap.put(humidifier.getId(), mapper.fromHumidifierComponentList(humidifier.getHumidifierComponents()));
        }
        return componentMap;
    }

    @Override
    public HumidifierComponent findById(Long id) {
        return null;
    }

    @Override
    public void save(HumidifierComponent humidifierComponent) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<HumidifierComponentDto> findAllByIds(List<Long> ids) {
        return mapper.fromHumidifierComponentList(humidifierComponentRepository.findAllById(ids));
    }
}
