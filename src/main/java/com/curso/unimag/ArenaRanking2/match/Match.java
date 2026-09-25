package com.curso.unimag.ArenaRanking2.match;

import com.curso.unimag.ArenaRanking2.teams.Team;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// tipo entity para poder inyectar los beans
@Entity
// entidad de tabla para poder
// tratarla en la persistencia
@Table(name = "matches")
@Setter // para no poner setters
@Getter // para no poner getters
@NoArgsConstructor // genere un constructor sin argumentos
@AllArgsConstructor // genere un constructor con todos los argumentos
@Builder // para cuando son diferentes atributos seteados
public class Match {

    @Id // identificador, generado como id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // solamente trae el team id si se pide
    // no trae todos los datos de golpe, toca
    // especificarle si se quieren todos los datos
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // nombre de la foreign key = "team_id"
    @JoinColumn(name = "team_id",nullable = false)
    private Team team;

    // string de 100 char no nulo
    @Column(nullable = false,length = 100)
    private String opponent;

    // string de 100 char no nulo
    @Column(length = 100)
    private String tournament;
    // integer de puntuacion no nulo
    @Column(name = "team_score", nullable = false)
    private Integer teamScore;

    // integer de puntuacion oponente no nulo
    @Column(name = "opponent_score",nullable = false)
    private Integer opponentScore;

    // un string no nulo de 10 characteres que puede ser
    // WIN, DEFEAT, DRAW
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    private MatchResult result;

    //
    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // antes de guardar a persistencia
    // que se coloque la fecha actual
    @PrePersist
    void onCreate(){
        // fecha actual
        this.createdAt = LocalDateTime.now();
    }

}
