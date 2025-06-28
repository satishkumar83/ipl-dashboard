package io.javabrains.ip_dashboard.data;

import io.javabrains.ip_dashboard.model.Match;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {
    @Override
    public Match process(MatchInput matchInput) throws Exception {
        Match match = new Match();
        match.setId(matchInput.getId());
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setMatch_type(matchInput.getMatch_type());
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());
        match.setTeam1(matchInput.getTeam1());
        match.setTeam2(matchInput.getTeam2());
        match.setTossDecision(matchInput.getToss_decision());
        match.setTossWinner(matchInput.getToss_winner());
        match.setWinner(matchInput.getWinner());
        return match;
    }
}
