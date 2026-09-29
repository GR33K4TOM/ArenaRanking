package com.curso.unimag.ArenaRanking2.match.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MatchRequest(
     // procesar los dtos
    // antes de mapearlos 
    // a entidades
    @NotBlank(message = "Cannot be blank")
    @Size(max = 100)
    String opponent,
    
    @Size(max = 100)
    String tournament,

    // para evitar nulidad dentro
    // de una base de datos
    @NotNull(message = "opponent score is required")
    @Min(value = 0, message = "Score cannot be negative")
    Integer teamScore,

    @NotNull (message = "opponent score is required")
    @Min(value = 0, message = "Score cannot be negative")
    Integer opponentScore,

    @NotNull(message = "Played date is required")
    LocalDateTime playedAt
)
{}