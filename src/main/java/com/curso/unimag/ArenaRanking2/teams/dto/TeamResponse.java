package com.curso.unimag.ArenaRanking2.teams.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TeamResponse(
    // que se devuelve dentro del dto

    // comparar siempre con los campos del request
    Long id,
    String name,
    String tag,
    String region,
    String logoUrl,
    LocalDate foundedDate,
    LocalDateTime createdAt
) {
}

