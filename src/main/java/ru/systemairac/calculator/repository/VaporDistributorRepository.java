package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;

public interface VaporDistributorRepository extends JpaRepository<VaporDistributor,Long> {
    public VaporDistributor findFirstByIdAndDiameterAndLengthLessThan(Long id,int diameter,int length);
}
