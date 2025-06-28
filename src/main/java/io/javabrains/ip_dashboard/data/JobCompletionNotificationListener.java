package io.javabrains.ip_dashboard.data;

import io.javabrains.ip_dashboard.model.Match;
import io.javabrains.ip_dashboard.model.Team;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager em;

    @Autowired
    public JobCompletionNotificationListener(EntityManager em, JdbcTemplate jdbcTemplate) {
        this.em = em;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, Team> teamsMap = new HashMap<>();

            em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (long) e[1]))
                    .forEach(team -> teamsMap.put(team.getTeamName(), team));

            em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .forEach(e -> {
                        Team team = teamsMap.get((String)e[0]);
                        team.setTotalMatches(team.getTotalMatches()+(long)e[1]);
                    });
            em.createQuery("select m.winner, count(*) from Match m group by m.winner", Object[].class)
                    .getResultList()
                    .forEach(e -> {
                        Team team = teamsMap.get((String)e[0]);
                        if(team!=null) team.setTotalWins((long)e[1]);
                    });

            teamsMap.values().forEach(em::persist);
            Random random = new Random();
            teamsMap.values().forEach(em-> {
                jdbcTemplate.update("INSERT into team (id, team_name, total_wins, total_matches) VALUES(?, ?, ?, ?)", random.nextInt(), em.getTeamName(), em.getTotalWins(), em.getTotalMatches());
            });
            log.info("Entity-1: {}", em.find(Team.class, 1L).toString());
            log.info("Entity-2: {}", em.find(Team.class, 2L).toString());
            teamsMap.values().forEach(team -> log.info(team.toString()));
        }
    }
}
