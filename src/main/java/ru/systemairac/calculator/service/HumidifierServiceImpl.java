package ru.systemairac.calculator.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.HumidifierType;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.mapper.HumidifierMapper;
import ru.systemairac.calculator.mapper.UserMapper;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.repository.HumidifierRepository;
import ru.systemairac.calculator.repository.HumidifierTypeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class HumidifierServiceImpl implements HumidifierService {

    private final HumidifierRepository humidifierRepository;
    private static final int LIMIT = 3;
    private final HumidifierTypeRepository humidifierRepositoryType;
    private final HumidifierMapper mapper = HumidifierMapper.MAPPER;

    @PersistenceContext
    private EntityManager em;

    public HumidifierServiceImpl(HumidifierRepository humidifierRepository, HumidifierTypeRepository humidifierRepositoryType) {
        this.humidifierRepository = humidifierRepository;
        this.humidifierRepositoryType = humidifierRepositoryType;
        init();
    }

    public void init(){
        EnumHumidifierType type1 = EnumHumidifierType.ELECTRODE;
        EnumHumidifierType type2 = EnumHumidifierType.HEATING_ELEMENT;
        List<Humidifier> humidifiers = Arrays.asList(Humidifier.builder()
                        .id(1L)
                        .articleNumber("123")
                        .electricPower(7)
                        .capacity(25)
                        .humidifierType(type1)
                        .phase(3)
                        .vaporPipeDiameter(25)
                        .numberOfCylinders(1)
                        .voltage(380)
                        .price(BigDecimal.valueOf(1500))
                        .build(),
                Humidifier.builder()
                        .id(2L)
                        .articleNumber("1")
                        .electricPower(5)
                        .humidifierType(type2)
                        .capacity(20)
                        .phase(3)
                        .vaporPipeDiameter(25)
                        .numberOfCylinders(1)
                        .voltage(380)
                        .price(BigDecimal.valueOf(1000))
                        .build());
        humidifierRepository.saveAll(humidifiers);
    }
    @Override
    public List<HumidifierDto> findHumidifiersNamed(double power,  int phase,EnumHumidifierType humidifierType) {
        return mapper.fromHumidifierList(em.createNamedQuery("findRequiredHumidifiers",Humidifier.class)
                .setParameter("capacity",power)
                .setParameter("phase",phase)
                .setParameter("humidifierType",humidifierType)
                .setMaxResults(LIMIT).getResultList());
    }

    @Override
    public List<Humidifier> findHumidifiers(double power, EnumHumidifierType humidifierType, int phase) {
        return humidifierRepository.findDistinctFirst3ByCapacityGreaterThanEqualAndHumidifierTypeEqualsAndPhaseOrderByCapacity(power,humidifierType,phase);
    }

    @Override
    public List<HumidifierDto> findDtoHumidifiers(double power, int phase, EnumHumidifierType humidifierType) {
        List<Humidifier> humidifiers = humidifierRepository.findDistinctFirst3ByCapacityGreaterThanEqualAndHumidifierTypeEqualsAndPhaseOrderByCapacity(power,humidifierType,phase);
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
    public void save(HumidifierType humidifierType) {
        humidifierRepositoryType.save(humidifierType);
    }

    @Override
    public void saveAll(List<Humidifier> humidifier) {
        humidifierRepository.saveAll(humidifier);
    }

    @Override
    public void deleteById(Long id) {
    }
}
