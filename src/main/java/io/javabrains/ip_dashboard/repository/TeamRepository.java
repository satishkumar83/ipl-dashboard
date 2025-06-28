package io.javabrains.ip_dashboard.repository;

import io.javabrains.ip_dashboard.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamName(String teamName);
}
