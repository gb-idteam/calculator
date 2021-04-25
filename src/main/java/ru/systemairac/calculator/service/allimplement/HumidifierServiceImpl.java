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
import ru.systemairac.calculator.repository.humidifier.HumidifierFilter;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierSpecification;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.util.HashMap;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    private static final int NUMBER_OF_RESULTS = 3;
    private final HumidifierRepository humidifierRepository;

    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;

    public HumidifierServiceImpl(HumidifierRepository humidifierRepository) {
        this.humidifierRepository = humidifierRepository;
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
        return mapper.fromHumidifierList(findHumidifiers(minimalCapacity, voltage, type));
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
