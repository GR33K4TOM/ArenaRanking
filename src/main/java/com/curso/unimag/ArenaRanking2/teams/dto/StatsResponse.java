package com.curso.unimag.ArenaRanking2.teams.dto;

public record StatsResponse(
    // como se responde la solicitud
    // de estadisticas del dto
   Long teamId,
   Long totalMatches,
   Long wins,
   Long losses,
   Long draws,
   // promedio de victorias
   double winRate 
) {}
