package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.VaporDistributorDto;
import ru.systemairac.calculator.mapper.VaporDistributorMapper;
import ru.systemairac.calculator.repository.VaporDistributorRepository;
import ru.systemairac.calculator.service.allinterface.VaporDistributorService;

import java.util.HashMap;
import java.util.List;

@Service
public class VaporDistributorServiceImpl implements VaporDistributorService {
    private final VaporDistributorRepository vaporDistributorRepository;
    private final VaporDistributorMapper mapper = VaporDistributorMapper.MAPPER;

    public VaporDistributorServiceImpl(VaporDistributorRepository vaporDistributorRepository) {
        this.vaporDistributorRepository = vaporDistributorRepository;
    }

    @Override
    public void delete(Long id) {
        vaporDistributorRepository.deleteById(id);
    }

    @Override
    public void save(VaporDistributor vaporDistributor) {
        vaporDistributorRepository.save(vaporDistributor);
    }

    @Override
    public HashMap<Long, VaporDistributorDto> getMapDistributorsByIds(int width, HashMap<Long, Integer> diameters, List<Humidifier> humidifierList) {
        HashMap<Long , VaporDistributorDto> distributorsMap = new HashMap<>();
        humidifierList.forEach(
                humidifier -> distributorsMap.put(
                        humidifier.getId(),
                        mapper.fromVaporDistributor(
                                mapDistributorsByIds(humidifier.getId(),diameters.get(humidifier.getId()),width))));
        return distributorsMap;
    }

    private VaporDistributor mapDistributorsByIds(Long id, Integer diameter, int width) {
        // TODO проверить на сколько меньше, чем ширина
        return vaporDistributorRepository.findFirstByDiameterAndLengthLessThanOrderByLengthDesc(diameter,width - 50);
    }
}
