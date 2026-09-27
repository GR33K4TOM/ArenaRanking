package com.curso.unimag.ArenaRanking2.teams.dto;

public record StreakResponse(
    // que se espera al 
    // pedir una racha
    Long teamId,
    int currentStreak
) {
}
