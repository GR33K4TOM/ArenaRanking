package com.curso.unimag.ArenaRanking2.teams;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.curso.unimag.ArenaRanking2.teams.dto.TeamRequest;
import com.curso.unimag.ArenaRanking2.teams.dto.TeamResponse;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    // ignore tanto id, como fecha de creacion
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    Team toEntity(TeamRequest request);
    // que tenga un metodo que devuelva
    // un team

    TeamResponse toResponse(Team team);

    @Mapping(target = "id", ignore = true)
    @Mapping (target = "createdAt", ignore = true)
    // que defina quien es target dentro de los parametros
    void updateEntity(TeamRequest request, @MappingTarget Team team);
}
