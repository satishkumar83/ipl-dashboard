package io.javabrains.ip_dashboard.controller;

import io.javabrains.ip_dashboard.model.Team;
import io.javabrains.ip_dashboard.repository.MatchRepository;
import io.javabrains.ip_dashboard.repository.TeamRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository){
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        Team team = teamRepository.findByTeamName(teamName);
        Pageable pageable = PageRequest.of(0, 2);
        team.setMatches(matchRepository.findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable));
        return team;
    }
}
