package io.javabrains.ip_dashboard.controller;

import io.javabrains.ip_dashboard.model.Match;
import io.javabrains.ip_dashboard.model.Team;
import io.javabrains.ip_dashboard.repository.MatchRepository;
import io.javabrains.ip_dashboard.repository.TeamRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year){
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year+1, 1, 1);
        return matchRepository.findMatchesByTeamNameBetweenDates(teamName, startDate, endDate);
    }
}
