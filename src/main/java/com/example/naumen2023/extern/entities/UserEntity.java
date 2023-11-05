package com.example.naumen2023.extern.services.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_entity")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idUser;

    public String firstname;
    public String lastname;
    public String surname;
    public String username;
    @Size(min = 8, message = "Минимальная длина пароля 8 символов")
    public String password;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    public Set<Roles> roles = new HashSet<>();
}
