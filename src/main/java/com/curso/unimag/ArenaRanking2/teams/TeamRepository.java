package com.curso.unimag.ArenaRanking2.teams;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>{
    // encontrar regi
    Page<Team> findByRegionIgnoreCase(String region, Pageable pageable);
    // encontrar por nombre ignorar mayusculas/minusculas
    Optional<Team> findByNameIgnoreCase(String name);
}
