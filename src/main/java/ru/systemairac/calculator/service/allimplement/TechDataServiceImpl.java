package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.mapper.TechDataMapper;
import ru.systemairac.calculator.repository.CalculationRepository;
import ru.systemairac.calculator.repository.TechDataRepository;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.TechDataService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TechDataServiceImpl implements TechDataService {

    private TechDataRepository repository;

    private final TechDataMapper mapper = TechDataMapper.MAPPER;

    public TechDataServiceImpl(TechDataRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void save(TechDataDto dto, Calculation calculation) {
        TechData techData = mapper.toTechData(dto);
        techData.setCalculation(calculation);
        repository.save(techData);
    }

    @Override
    public Optional<TechData> getById(long id) {
        return repository.findById(id);
    }
}
