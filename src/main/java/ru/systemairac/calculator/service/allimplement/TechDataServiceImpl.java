package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.mapper.TechDataMapper;
import ru.systemairac.calculator.repository.CalculationRepository;
import ru.systemairac.calculator.repository.TechDataRepository;
import ru.systemairac.calculator.service.allinterface.TechDataService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TechDataServiceImpl implements TechDataService {

    private final TechDataRepository repository;
    private final CalculationRepository calculationRepository;

    private final TechDataMapper mapper = TechDataMapper.MAPPER;

    public TechDataServiceImpl(TechDataRepository repository, CalculationRepository calculationRepository) {
        this.repository = repository;
        this.calculationRepository = calculationRepository;
    }

    @Override
    @Transactional
    public Long save(TechDataDto dto, Long idCalc) {
        TechData techData = mapper.toTechData(dto);
        Calculation calculation = calculationRepository.findById(idCalc).orElseThrow();
        techData.setCalculation(calculation);
        return repository.save(techData).getId();
    }

    @Override
    public Optional<TechData> getById(long id) {
        return repository.findById(id);
    }
    @Override
    public TechDataDto findById(long id) {
        return mapper.fromTechData(getById(id).orElse(null));
    }
}
