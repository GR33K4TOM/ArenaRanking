package com.curso.unimag.ArenaRanking2.teams;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.unimag.ArenaRanking2.teams.dto.StatsResponse;
import com.curso.unimag.ArenaRanking2.teams.dto.StreakResponse;
import com.curso.unimag.ArenaRanking2.teams.dto.TeamRequest;
import com.curso.unimag.ArenaRanking2.teams.dto.TeamResponse;

public interface TeamService {

    // que se puede hacer con los equipos
    // definido como una interfaz
    
    TeamResponse create(TeamRequest request);
    
    Page<TeamResponse> list(String region, Pageable pageable);

    TeamResponse getById(Long id);

    void delete(Long id);

    StatsResponse getStats(Long id);

    StreakResponse getStreak(Long id);
}
