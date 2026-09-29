package com.curso.unimag.ArenaRanking2.match.dto;

import java.time.LocalDateTime;

import com.curso.unimag.ArenaRanking2.match.MatchResult;


public record MatchResponse(
    Long id,
    Long  teamId,
    String opponent,
    Integer teamScore,
    MatchResult result,
    LocalDateTime playedAt,
    LocalDateTime createdAt
) {
}
