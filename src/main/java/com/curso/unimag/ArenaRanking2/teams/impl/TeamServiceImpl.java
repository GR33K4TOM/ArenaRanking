package com.curso.unimag.ArenaRanking2.teams.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.curso.unimag.ArenaRanking2.exception.DataConflictException;
import com.curso.unimag.ArenaRanking2.exception.ResourceNotFoundException;
import com.curso.unimag.ArenaRanking2.match.Match;
import com.curso.unimag.ArenaRanking2.match.MatchRepository;
import com.curso.unimag.ArenaRanking2.match.MatchResult;
import com.curso.unimag.ArenaRanking2.teams.Team;
import com.curso.unimag.ArenaRanking2.teams.TeamMapper;
import com.curso.unimag.ArenaRanking2.teams.TeamRepository;
import com.curso.unimag.ArenaRanking2.teams.TeamService;
import com.curso.unimag.ArenaRanking2.teams.dto.StatsResponse;
import com.curso.unimag.ArenaRanking2.teams.dto.StreakResponse;
import com.curso.unimag.ArenaRanking2.teams.dto.TeamRequest;
import com.curso.unimag.ArenaRanking2.teams.dto.TeamResponse;

import lombok.RequiredArgsConstructor;

@Service  // es un bean de servicio
@RequiredArgsConstructor
@Transactional(readOnly = true) // si hay error, haga rollback
public class TeamServiceImpl implements TeamService{

    // los 3 beans que van a ser inyectados
    // y usados
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final TeamMapper teamMapper;

     
    @Transactional 
    public TeamResponse create(TeamRequest request) {
        teamRepository.findByNameIgnoreCase(request.name()).ifPresent(e->{
            throw new DataConflictException("A team with name" + request.name()+" already exist");
    });
        Team team = teamMapper.toEntity(request);
        return teamMapper.toResponse(teamRepository.save(team));
    }

    public Page<TeamResponse> list(String region, Pageable pageable) {
        Page<Team> page = StringUtils.hasText(region) 
        ? teamRepository.findByRegionIgnoreCase(region,pageable)
        : teamRepository.findAll(pageable);
        return page.map(teamMapper::toResponse);
    }

    @Override
    public TeamResponse getById(Long id) {
        return teamMapper.toResponse(findOrFail((id)));
    }


    @Transactional 
    public TeamResponse update(Long id, TeamRequest request){
        Team team = findOrFail(id);
        // primero buscar en la base de datos esa id del teams a actualizar
        teamRepository.findByNameIgnoreCase(request.name()).filter(other->!other
            .getId().equals(id)).ifPresent(e->{
                throw new DataConflictException("A team with name "+request.name()+" already exist");
            });
            teamMapper.updateEntity(request, team);
            return teamMapper.toResponse(teamRepository.save(team));
    }

    @Transactional 
    public void delete(Long id) {
     Team team = findOrFail(id);
     teamRepository.delete(team);
    }

    
    public StatsResponse getStats(Long id) {
        findOrFail(id);
        Long total = matchRepository.countByTeamId(id);
        Long wins = matchRepository.countByTeamIdAndResult(id,MatchResult.WIN);
        Long losses = matchRepository.countByTeamIdAndResult(id,MatchResult.LOSS);
        Long draws = matchRepository.countByTeamIdAndResult(id,MatchResult.DRAW);
        double winRate = total == 0 ? 0.0 : (wins*100)/total;
        return  new  StatsResponse(id,total,wins,losses,draws,winRate);
    }

    
    public StreakResponse getStreak(Long id) {
    findOrFail(id);
    List<Match> matches = matchRepository.findByTeamIdOrderByPlayedAtDesc(id);
    int streak = 0;
    for(Match match : matches){
        if (match.getResult() == MatchResult.WIN) {
            streak++;
        }
        else    {
            break;
                }

    } 
    return new StreakResponse(id, streak); 
}
    
    public Team findOrFail(Long id){
        return teamRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Team not found")
         );
        
    }

}
