package com.curso.unimag.ArenaRanking2.teams;

import com.curso.unimag.ArenaRanking2.match.Match;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// tipo entity para poder inyectar los beans
@Entity
// entidad de tabla para poder
// tratarla en la persistencia
@Table(name = "teams")
@Setter // para no poner setters
@Getter // para no poner getters
@NoArgsConstructor // genere un constructor sin argumentos
@AllArgsConstructor // genere un constructor con todos los argumentos
@Builder // para cuando son diferentes atributos seteados
public class Team {

    // identificador del equipo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nombre del equipo que debe ser unico y no nulo
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // identificador tag unico no nulo de longitud 10
    @Column(nullable = false, unique = true, length = 10)
    private String tag;

    // region que puede ser nula y no unica
    @Column(length = 50)
    private String region;

    // url para el logo del equipo
    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    // fecha de tipo fecha en la que se fundo el
    // equipo
    @Column(name = "founded date")
    private LocalDate foundedDate;

    // columna no nula inmutable de tipo fecha
    @Column(name = "created at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Match> matches = new ArrayList<>();

    // antes de guardar a persistencia
    // que se coloque la fecha actual
    @PrePersist
    void onCreate(){
        // fecha actual
        this.createdAt = LocalDateTime.now();
    }
}
