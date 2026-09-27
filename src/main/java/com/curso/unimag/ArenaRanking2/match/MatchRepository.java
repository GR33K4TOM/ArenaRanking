package com.curso.unimag.ArenaRanking2.match;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Page<Match> findByTeamId(Long teamId, Pageable pageable);

    Page<Match> findByTeamIdAndTournamentIgnoreCase(Long teamId, String tournament, Pageable pageable);

    Long countByTeamId(Long teamId);

    Long countByTeamIdAndResult(Long teamId, MatchResult result);

    List<Match> findByTeamIdOrderByPlayedAtDesc(Long eamId);
}
