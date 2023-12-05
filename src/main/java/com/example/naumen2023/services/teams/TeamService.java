package com.example.naumen2023.services.teams;

import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void save(TeamEntity teamEntity){
        teamRepository.save(teamEntity);
    }

    public List<TeamEntity> getAll() {
        return teamRepository.findAll();
    }

    public void delete(TeamEntity team) {
        for(UserEntity user: team.getParticipants()){
            user.setTeam(null);
        }
        teamRepository.delete(team);
    }

    public TeamEntity findById(int id){
        return teamRepository.findById(id).orElse(null);
    }
}
