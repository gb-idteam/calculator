package ru.systemairac.calculator.service;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierMapper;
import ru.systemairac.calculator.myEnum.HumidifierType;
import ru.systemairac.calculator.repository.HumidifierRepository;

@Service
public class HumidifierServiceImpl implements HumidifierService{

    private final HumidifierRepository humidifierRepository;
    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;

    public HumidifierServiceImpl(HumidifierRepository humidifierRepository) {
        this.humidifierRepository = humidifierRepository;
    }

    @Override
    public Humidifier getByMaxVaporOut(float maxVaporOut) {
        return humidifierRepository.findFirstByMaxVaporOutput(maxVaporOut);
    }

    @Override
    public Humidifier getByPhase(int phase) {
        return humidifierRepository.findFirstByPhase(phase);
    }

    @Override
    public Humidifier getByHumidifierType(HumidifierType humidifierType) {
        return humidifierRepository.findByHumidifierType(humidifierType);
    }

    @Override
    public boolean save(HumidifierDto humidifierDto) {
        Humidifier humidifier = Humidifier.builder()
                .maxVaporOutput(humidifierDto.getMaxVaporOutput())
                .phase((humidifierDto.getPhase()))
                .humidifierType(humidifierDto.getHumidifierType())
                .build();
        humidifierRepository.save(humidifier);
        return true;
    }

    @Override
    public HumidifierDto findById(Long id) {
        return mapper.fromHumidifier(humidifierRepository.getOne(id));
    }
}
