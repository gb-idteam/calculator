package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Project;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String password;
    private String matchingPassword;
    private String email;
    private List<Project> projects;
}
