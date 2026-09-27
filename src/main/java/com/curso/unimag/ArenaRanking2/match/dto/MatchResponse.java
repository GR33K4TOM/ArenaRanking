package com.curso.unimag.ArenaRanking2.match.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MatchResponse(
    @NotBlank (max =100)
    String opponent,
    
    @Size(max = 100)
) {
}
