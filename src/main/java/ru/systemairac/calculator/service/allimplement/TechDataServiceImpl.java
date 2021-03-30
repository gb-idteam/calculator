package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.mapper.TechDataMapper;
import ru.systemairac.calculator.repository.TechDataRepository;
import ru.systemairac.calculator.repository.UserRepository;
import ru.systemairac.calculator.service.allinterface.TechDataService;

import java.util.List;
import java.util.Optional;

@Service
public class TechDataServiceImpl implements TechDataService {

    private TechDataRepository repository;
    private UserRepository userRepository;

    private final TechDataMapper mapper = TechDataMapper.MAPPER;

    public TechDataServiceImpl(TechDataRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    /**
     * @param userId id пользователя, к которому относится TechData. Может быть null.
     */
    @Override
    public void save(TechDataDto dto, Long userId) {
        User user = null;
        if (userId != null)
            user = userRepository.findById(userId).orElseThrow();
        TechData techData = mapper.toTechData(dto);
        techData.setUser(user);
        repository.save(techData);
    }

    @Override
    public Optional<TechData> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<TechData> findByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return repository.findByUser(user);
    }
}
