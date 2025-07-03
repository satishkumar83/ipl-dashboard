package io.javabrains.ip_dashboard.repository;

import io.javabrains.ip_dashboard.model.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :startDate and :endDate order by m.date desc")
    List<Match> findMatchesByTeamNameBetweenDates(@Param("teamName") String teamName, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Match> findByTeam1OrTeam2AndDateBetweenOrderByDateDesc(String teamName1, String teamName2, LocalDate date1, LocalDate date2);
}
