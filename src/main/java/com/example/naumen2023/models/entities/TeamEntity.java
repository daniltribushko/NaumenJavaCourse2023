package com.example.naumen2023.models.entities;

import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.models.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "team_entity")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTeam;
    private String name;
    private String description;
    private String ChatURL;

    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    private Set<ProgrammingLanguageName> languages = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<UserEntity> participants;

    private Long idLeader;

}