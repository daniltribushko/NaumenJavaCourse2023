package com.example.naumen2023.entities;

import com.example.naumen2023.entities.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_entity")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String firstname;
    private String lastname;
    private String surname;
    private String username;
    @Size(min = 8, message = "Минимальная длина пароля 8 символов")
    private String password;

    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    private Set<Roles> roles = new HashSet<>();
}
