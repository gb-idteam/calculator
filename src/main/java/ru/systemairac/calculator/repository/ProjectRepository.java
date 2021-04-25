package ru.systemairac.calculator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByUser(User user);
    Project findByUserAndTitleAndAddress(User user,String title, String address);
}
