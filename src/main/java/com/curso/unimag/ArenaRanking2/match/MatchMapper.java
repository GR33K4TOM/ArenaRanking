package com.curso.unimag.ArenaRanking2.match;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.curso.unimag.ArenaRanking2.match.dto.MatchRequest;
import com.curso.unimag.ArenaRanking2.match.dto.MatchResponse;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping (target ="id", ignore = true)
    @Mapping (target ="team", ignore = true)
    @Mapping (target ="result", ignore = true) 
    @Mapping (target ="createdAt", ignore = true) // no se debe proveer
    // la bd se encarga
    Match toEntity(MatchRequest request);

    @Mapping (target ="teamId", source = "team.id")
    MatchResponse toResponse(Match match);

    @Mapping (target ="id", ignore = true)
    @Mapping (target ="team", ignore = true)
    @Mapping (target ="result", ignore = true) 
    @Mapping (target ="createdAt", ignore = true) // no se debe proveer
    // la bd se encarga
    void updateEntity(MatchRequest request, @MappingTarget Match match); 
}
