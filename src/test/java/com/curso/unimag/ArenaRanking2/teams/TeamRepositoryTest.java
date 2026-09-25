package com.curso.unimag.ArenaRanking2.teams;

import com.curso.unimag.ArenaRanking2.PostgresTestContainerSupport;
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
        Team team = new Team();
    teamRepository.save(Team);

        Optional<Team> found = teamRepository.findByNameIgnoreCase("team vortex");
    }
}