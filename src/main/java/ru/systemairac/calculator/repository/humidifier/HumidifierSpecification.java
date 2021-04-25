package ru.systemairac.calculator.repository.humidifier;

import org.springframework.data.jpa.domain.Specification;
import ru.systemairac.calculator.domain.humidifier.Humidifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public final class HumidifierSpecification implements Specification<Humidifier> {

    private final HumidifierFilter filter;

    public HumidifierSpecification(HumidifierFilter filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Humidifier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getMinimalCapacity() != null) {
            predicates.add(cb.ge(root.get("capacity"), filter.getMinimalCapacity()));
        }

        if (filter.getVoltage() != null) {
            predicates.add(cb.equal(root.get("voltage"), filter.getVoltage()));
        }

        if (filter.getType() != null) {
            predicates.add(cb.equal(root.get("humidifierType"), filter.getType()));
        }

        return query.where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .orderBy(cb.asc(root.get("capacity")))
                .getRestriction();

    }
}

