package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Project;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String password;
    private String fullName;
    private String nameCompany;
    private String addressCompany;
    private String position;
    private boolean isConfirmed;
    private Long phone;
    private String email;
    private String matchingPassword;
    private List<Project> projects;
}
