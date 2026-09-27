package com.curso.unimag.ArenaRanking2.teams.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamRequest(  
    // que puede recibir el dto

    // antes de hacer la peticion 
    // a la base de datos
    // tener validaciones
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    String name,
    
    @NotBlank(message = "Tag is required")
    @Size(max = 10)
    String tag,

    @Size(max = 50)
    String region,

    String logoUrl,

    LocalDate foundedDate
)
{}
