package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.dto.HumidifierComponentDto;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierComponentMapper;
import ru.systemairac.calculator.repository.HumidifierComponentRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;

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
    }

    @Override
    public List<HumidifierComponent> getAllComponent() {
        return humidifierComponentRepository.findAll();
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
    public List<HumidifierComponent> findAllByIds(List<Long> ids) {
        return humidifierComponentRepository.findAllById(ids);
    }

    @Override
    public List<HumidifierComponentDto> getListDto(List<HumidifierComponent> humidifierComponents) {
        return mapper.fromHumidifierComponentList(humidifierComponents);
    }
}
