package ru.systemairac.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.systemairac.calculator.domain.Project;
import ru.systemairac.calculator.domain.User;
import ru.systemairac.calculator.dto.ProjectDto;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByUser(User user);
    Project findByUserAndTitleAndAddress(User user,String title, String address);
}
