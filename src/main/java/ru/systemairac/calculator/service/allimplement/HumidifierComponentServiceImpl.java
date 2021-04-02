package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierComponentMapper;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

@Service
public class HumidifierComponentServiceImpl implements HumidifierComponentService {

    private final HumidifierComponentMapper mapper = HumidifierComponentMapper.MAPPER;

    private HumidifierRepository humidifierRepository;

    public HumidifierComponentServiceImpl(HumidifierRepository humidifierRepository) {
        this.humidifierRepository = humidifierRepository;
    }

    @Override
    public List<HumidifierComponent> getAllComponent() {
        return null;
    }

    @Override
    public HashMap<String, List<HumidifierComponentDto>> getAllComponentByHumidifiers(List<HumidifierDto> humidifiers) {
        List<Long> ids = humidifiers.stream().map(HumidifierDto::getId).collect(Collectors.toList());
        List<Humidifier> humidifierList = humidifierRepository.findAllById(ids);
        HashMap<String , List<HumidifierComponentDto>> componentMap = new HashMap<>();
        humidifierList.forEach(humidifier -> componentMap.put(humidifier.getArticleNumber(), mapper.fromHumidifierComponentList(humidifier.getHumidifierComponents())));
        return componentMap;
    }

    @Override
    public HashMap<String, List<HumidifierComponentDto>> findByHumidifier(HumidifierDto humidifierDto) {
        Humidifier humidifier = humidifierRepository.findHumidifierById(humidifierDto.getId());
        HashMap<String,List<HumidifierComponentDto>> componentMap = new HashMap<>();
        if(humidifier != null){
            componentMap.put(humidifier.getArticleNumber(), mapper.fromHumidifierComponentList(humidifier.getHumidifierComponents()));
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
}
