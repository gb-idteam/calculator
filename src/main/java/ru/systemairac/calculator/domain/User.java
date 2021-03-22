package ru.systemairac.calculator.domain;

import lombok.*;
import ru.systemairac.calculator.myEnum.Role;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_users")
public class User {
    private static final String SEQ_NAME = "user_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;

    private String fullName;
    private String nameCompany;
    private String addressCompany;
    private String post;
    private Long phone;
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Project> projects;



}
