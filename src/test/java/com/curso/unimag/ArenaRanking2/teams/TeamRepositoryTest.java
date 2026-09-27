package com.curso.unimag.ArenaRanking2.teams;

import com.curso.unimag.ArenaRanking2.PostgresTestContainerSupport;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
        //gracias al @Builder, se puede definir el objeto mas facilmente
        //asi
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
    Assertions.assertThat(found).isEmpty();       
    }

    @Test
    void shouldFilterTeamByRegionIgnoringCaseAndPaginate(){
        teamRepository.save(Team
            .builder() // pedir el patron builder
            .name("Team A") // equivalente a decir team.setname = "Team A"
            .tag("TA")
            .region("LATAM")
            .build()); // finalmente, pedir que construya con esos parametros 
            
        teamRepository.save(Team
            .builder() // pedir el patron builder
            .name("Team B") // equivalente a decir team.setname = "Team B"
            .tag("TB")
            .region("latam")
            .build()); // finalmente, pedir que construya con esos parametros 
        
        teamRepository.save(Team
            .builder() // pedir el patron builder
            .name("Team C") // equivalente a decir team.setname = "Team C"
            .tag("TC")
            .region("EU")
            .build()); // finalmente, pedir que construya con esos parametros 
            
        Page<Team> page = teamRepository.findByRegionIgnoreCase(
            "LATAM", PageRequest.of(0, 10));
            // las paginaciones que se esperan son 2; 2 equipos de "latam", 
            // ignorando mayusculas
            Assertions.assertThat(page.getTotalElements())
            .isEqualTo(2); 
            
            // afirmar que las paginaciones contienen exactamente
            // en cualquier orden equipo
            // A y equipo B
            Assertions.assertThat(page.getContent())
            // extraer del contenido del paginado
            // los nombres y evaluarlos
            .extracting(Team::getName) // funcion lambda (?)
            .containsExactlyInAnyOrder
            ("Team A", "Team B");
        }
    @Test 
    void shouldEnforceUniqueNameConstraint(){
        // construir un objeto y guardarlo 
        // en la base de datos
        teamRepository.save(Team.builder()
        .name("Team Repository Duplicate")
        .tag("TD1")
        .region("Eu")
        .build());
        // borra cualquier dato pendiente en 
        // la base de datos
        teamRepository.flush();

        // generar un equipo de mismo nombre
        Team duplicate = Team.builder()
        .name("Team Repository Duplicate")
        .tag("TD2")
        .region("Eu")
        .build();
        
        // traer la clase especifica de Assertions 
        org.junit.jupiter.api.Assertions.
               assertThrows(
            /* ^^^^^^^^ espera el tipo de error
             y su ejecutable
            
         */
            /*
            lanzar una excepcion de tipo
            violacion de integridad de datos
            */org.springframework.dao.DataIntegrityViolationException.class,
            () -> {
                teamRepository.save(duplicate);
                teamRepository.flush();
            }
        );
    }

}