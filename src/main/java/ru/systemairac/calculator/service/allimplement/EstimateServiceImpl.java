package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.Calculation;
import ru.systemairac.calculator.domain.Estimate;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.EstimateDto;
import ru.systemairac.calculator.mapper.EstimateMapper;
import ru.systemairac.calculator.repository.CalculationRepository;
import ru.systemairac.calculator.repository.EstimateRepository;
import ru.systemairac.calculator.repository.VaporDistributorRepository;
import ru.systemairac.calculator.repository.humidifier.HumidifierRepository;
import ru.systemairac.calculator.service.allinterface.EstimateService;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class EstimateServiceImpl implements EstimateService {
    private final EstimateMapper mapper = EstimateMapper.MAPPER;
    private final CalculationRepository calculationRepository;
    private final EstimateRepository estimateRepository;
    private final HumidifierRepository humidifierRepository;
    private final VaporDistributorRepository vaporDistributorRepository;
    private final HumidifierComponentService humidifierComponentService;

    public EstimateServiceImpl(CalculationRepository calculationRepository, EstimateRepository estimateRepository, HumidifierRepository humidifierRepository, VaporDistributorRepository vaporDistributorRepository, HumidifierComponentService humidifierComponentService) {
        this.calculationRepository = calculationRepository;
        this.estimateRepository = estimateRepository;
        this.humidifierRepository = humidifierRepository;
        this.vaporDistributorRepository = vaporDistributorRepository;
        this.humidifierComponentService = humidifierComponentService;
    }

    @Override
    @Transactional
    public EstimateDto save(Long calculationId, Long idHumidifier, Long[] idSelectedOptions, Long idDistributor) {
        if (calculationId==null || idHumidifier==null) return null;
        Calculation calculation = calculationRepository.findById(calculationId).orElseThrow();
        Humidifier humidifier = humidifierRepository.findById(idHumidifier).orElseThrow();
        List<HumidifierComponent> options=null;
        VaporDistributor distributor = null;
        if (idSelectedOptions!=null)
            options = humidifierComponentService.findAllByIds(Arrays.asList(idSelectedOptions));
        if (idDistributor!=null)
            distributor  = vaporDistributorRepository.findById(idDistributor).orElseThrow();
        Estimate estimate = Estimate.builder().
                humidifier(humidifier).
                vaporDistributor(distributor).
                humidifierComponents(options).
                build();
        estimateRepository.save(estimate);
        calculation.setEstimate(estimate);
        calculationRepository.save(calculation);
        return mapper.fromEstimate(estimate);
    }
}
