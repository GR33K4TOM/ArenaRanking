package com.curso.unimag.ArenaRanking2.match;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.unimag.ArenaRanking2.match.dto.MatchRequest;
import com.curso.unimag.ArenaRanking2.match.dto.MatchResponse;

public interface MatchService {

    // definir que tipo de uso se le puede dar a match
    // a traves de interfaces

    MatchResponse create(Long teamId, MatchRequest request);

    Page<MatchResponse> listByTeam(Long teamId, String tournament, Pageable pageable);

    MatchResponse update(Long teamId, Long matchId, MatchRequest request);

    void delete(Long teamId, Long matchId);
}
