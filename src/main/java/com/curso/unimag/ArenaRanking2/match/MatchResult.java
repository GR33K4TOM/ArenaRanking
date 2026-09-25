package com.curso.unimag.ArenaRanking2.match;

public enum MatchResult {
    WIN, LOSS, DRAW;
    public static MatchResult calculate(int teamScore, int opponentScore){
     if (teamScore < opponentScore)
         return  LOSS;
     if (teamScore > opponentScore)
         return  WIN;
     return DRAW;
    }
}
