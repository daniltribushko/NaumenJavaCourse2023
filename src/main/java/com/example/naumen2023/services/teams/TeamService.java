package com.example.naumen2023.services.teams;

import com.example.naumen2023.models.entities.TeamEntity;
import com.example.naumen2023.models.entities.UserEntity;
import com.example.naumen2023.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamEntity findById(int id){
        return teamRepository.findById(id).orElse(null);
    }

    public List<TeamEntity> getAll(List<TeamEntity> teamEntities) {
        List<TeamEntity> teams = teamRepository.findAll();
        teams.removeAll(teamEntities);
        return teams;
    }

    public void deleteTeam(TeamEntity team) {
        for (UserEntity user : team.getParticipants()){
            user.setTeam(null);
        }
        for (UserEntity user : team.getRequests()){
            user.getRequests().remove(team);
        }
        teamRepository.save(team);
        teamRepository.delete(team);
    }

    public void createTeam(UserEntity user, TeamEntity team) {
        user.setTeamStatus("leader");
        user.getRequests().clear();
        user.setTeam(team);

        team.getParticipants().add(user);
        team.setIdLeader(user.getIdUser());
        teamRepository.save(team);
    }

    public void addRequest(UserEntity user, String programmingLanguage, TeamEntity team) {
        user.setProgrammingLanguage(programmingLanguage);
        user.getRequests().add(team);

        team.getRequests().add(user);
        teamRepository.save(team);
    }

    public void deleteRequest(UserEntity user, TeamEntity team) {
        user.getRequests().remove(team);

        team.getRequests().remove(user);
        teamRepository.save(team);
    }

    public void acceptUser(UserEntity user, TeamEntity team) {
        user.getRequests().remove(team);
        user.setTeam(team);

        team.getRequests().remove(user);
        team.getParticipants().add(user);
        teamRepository.save(team);

    }

    public void rejectUser(UserEntity user, TeamEntity team) {
        user.getRequests().remove(team);

        team.getRequests().remove(user);
        teamRepository.save(team);
    }

    public void leaveTeam(UserEntity user, TeamEntity team) {
        team.getParticipants().remove(user);
        user.setTeam(null);
        teamRepository.save(team);
    }
}
