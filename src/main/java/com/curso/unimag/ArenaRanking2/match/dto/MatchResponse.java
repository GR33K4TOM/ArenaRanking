package com.curso.unimag.ArenaRanking2.match.dto;

import java.time.LocalDateTime;
import java.util.regex.MatchResult;

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
