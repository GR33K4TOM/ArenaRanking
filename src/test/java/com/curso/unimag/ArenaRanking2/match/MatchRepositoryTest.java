package com.curso.unimag.ArenaRanking2.match;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions; /* esencia */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.curso.unimag.ArenaRanking2.PostgresTestContainerSupport;
import com.curso.unimag.ArenaRanking2.teams.Team;
import com.curso.unimag.ArenaRanking2.teams.TeamRepository;

class MatchRepositoryTest extends PostgresTestContainerSupport {

    @Autowired 
    private MatchRepository matchRepository;

    @Autowired 
    private TeamRepository teamRepository;

    // team que se usara en todos los test
    private Team team;

    @BeforeEach 
    void setUp(){
        // limpiar todos los datos 
        // de ambas bases de datos
        matchRepository.deleteAll();
        teamRepository.deleteAll();

        // construir con patron
        // builder
        team = teamRepository
        .save(Team.builder()
        .name("Team Vortex")
        .tag("TVX")
        .region("LATAM")
        .build());
    }
    // para crear un match
    private Match newMatch(String opponentString, String tournamentString, int teamScore, int opponentScore, LocalDateTime playedAt){
        return Match.builder()
        .team(team)
        .opponent(opponentString)
        .tournament(tournamentString)
        .teamScore(teamScore)
        .opponent(opponentString)
        .playedAt(playedAt)
        .build();
    }

    @Test 
     void shouldFindMatchesByTeamIdPaginated(){
        matchRepository.save(newMatch("Rival A"
        , "Cup2025"
        , 2
        , 1
        , LocalDateTime.now()));

        matchRepository.save(newMatch("Rival B"
        , "Cup2025"
        , 1
        , 1
        , LocalDateTime.now()));

        Page<Match> page = matchRepository.
        findByTeamId(team.getId(), 
    /* pagineo de tamaño 10*/ PageRequest.of(0, 10));
    // el total de elementos del pagineo 
    // debe ser igual a 0
    Assertions.assertThat(page.getTotalElements()).isEqualTo(2);
    }
    @Test 
     void shouldFilterMatchesByTournamentIgnoringCase(){
        
        matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 2
        , 1
        , LocalDateTime.now()));

        matchRepository.save(newMatch("Rival A"
        , "League"
        , 1
        , 1
        , LocalDateTime.now()));

        Page<Match> page = matchRepository.findByTeamIdAndTournamentIgnoreCase(
            team.getId(),"cup2026"
            ,PageRequest.of(0, 10));

        Assertions.assertThat(page.getTotalElements()).isEqualTo(1);
        // encontrar el contenido en el indice 0
        Assertions.assertThat(page.getContent().get(0).getOpponent()).isEqualTo("Rival A");        

     }

     @Test 
     void shouldFindMatchesByTeamIdAndResult(){
        matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 3
        , 0
        , LocalDateTime.now()));
        matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 0
        , 2
        , LocalDateTime.now()));
        matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 1
        , 1
        , LocalDateTime.now()));

        // el team id debe ser igual a 3
        Assertions.assertThat(matchRepository.countByTeamId(team.getId())).isEqualTo(3);
        // cuantos partidos ha participado el equipo, y cuantas victorias / derrotas / empates ha tenido
        Assertions.assertThat(matchRepository.countByTeamIdAndResult(team.getId(),MatchResult.WIN)).isEqualTo(1);
        Assertions.assertThat(matchRepository.countByTeamIdAndResult(team.getId(),MatchResult.LOSS)).isEqualTo(1);
        Assertions.assertThat(matchRepository.countByTeamIdAndResult(team.getId(),MatchResult.DRAW)).isEqualTo(1);

    }
    @Test 
    void shouldOrderMatchesByPlayedAtDescending(){
matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 3
        , 0
        , LocalDateTime.now().minusDays(2)));
        matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 0
        , 2
        , LocalDateTime.now()));
        matchRepository.save(newMatch("Rival A"
        , "Cup2026"
        , 1
        , 1
        , LocalDateTime.now().minusDays(1)));
        
        List<Match> matches = matchRepository.findByTeamIdOrderByPlayedAtDesc(team.getId());
        
        // verificar que existe exactamente primero 
        // rival B, luego rival C, y finalmente rival A
        // al ordenarlos por tiempo jugado
        
        Assertions.assertThat(matches)
        .extracting(Match::getOpponent)
        .containsExactly("Rival B", "Rival C", "Rival A");
    }
}