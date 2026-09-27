package com.curso.unimag.ArenaRanking2.teams;

import com.curso.unimag.ArenaRanking2.PostgresTestContainerSupport;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class TeamRepositoryTest extends PostgresTestContainerSupport {


    // inyectar con autowired
    // porque no tenemos constructor que nos permita
    @Autowired
    private TeamRepository teamRepository;

    // antes de cada prueba
    // limpiar la db
    @BeforeEach
    void cleanDatabase(){
        teamRepository.deleteAll();
    }

    @Test
    void shouldFindTeamByNameIgnoringCase(){
        /*gracias al @Builder, se puede definir el objeto mas facilmente
        asi|*/
        teamRepository
        .save(Team.builder()
        .name("Team Vortex")
        .tag("TVX")
        .region("LATAM")
        .build());

        Optional<Team> teamFound = teamRepository.findByNameIgnoreCase("team vortex");
        
        Assertions.assertThat(teamFound).isPresent();
        Assertions.assertThat(teamFound.get().getTag()).isEqualTo("TVX");
    }


    @Test 
    void shouldReturnEmptyWhenNameDoesNotExist(){

    Optional<Team> found = teamRepository.findByNameIgnoreCase("Unknown Team");       
    }
}