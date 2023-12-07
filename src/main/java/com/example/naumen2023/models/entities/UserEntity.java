package com.example.naumen2023.models.entities;

import com.example.naumen2023.models.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_entity")
@Data
@JsonIgnoreProperties({"articlesList"})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String firstname;
    private String lastname;
    private String surname;
    private String username;
    private String password;
    private String email;

    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticleEntity> articlesList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_team")
    private TeamEntity team;

    @ManyToMany(mappedBy = "requests")
    private List<TeamEntity> requests;

    private String programmingLanguage;

    private String teamStatus;


}
