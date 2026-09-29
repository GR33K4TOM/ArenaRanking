package com.curso.unimag.ArenaRanking2.match.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.curso.unimag.ArenaRanking2.exception.ResourceNotFoundException;
import com.curso.unimag.ArenaRanking2.match.Match;
import com.curso.unimag.ArenaRanking2.match.MatchMapper;
import com.curso.unimag.ArenaRanking2.match.MatchRepository;
import com.curso.unimag.ArenaRanking2.match.MatchService;
import com.curso.unimag.ArenaRanking2.match.dto.MatchRequest;
import com.curso.unimag.ArenaRanking2.match.dto.MatchResponse;
import com.curso.unimag.ArenaRanking2.teams.Team;
import com.curso.unimag.ArenaRanking2.teams.impl.TeamServiceImpl;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchService{

    private final MatchRepository matchRepository;
    private final TeamServiceImpl teamService;
    private final MatchMapper matchMapper;

    @Transactional
    public MatchResponse create(Long teamId, MatchRequest request) {
        Team team = teamService.findOrFail(teamId);
        Match match = matchMapper.toEntity(request);
        match.setTeam(team);
        return matchMapper.toResponse(matchRepository.save(match));
    }

    public Page<MatchResponse> listByTeam(Long teamId, String tournament, Pageable pageable) {
        teamService.findOrFail(teamId);
        Page<Match> page = StringUtils.hasText(tournament)
                ? matchRepository.findByTeamIdAndTournamentIgnoreCase(teamId, tournament, pageable)
                : matchRepository.findByTeamId(teamId, pageable);
        return page.map(matchMapper::toResponse);
    }

    @Transactional
    public MatchResponse update(Long teamId, Long matchId, MatchRequest request) {
        Match match = findOfTeamOrFail(teamId, matchId);
        matchMapper.updateEntity(request, match);
        return matchMapper.toResponse(matchRepository.save(match));
    }

    @Transactional
    public void delete(Long teamId, Long matchId) {
        Match match = findOfTeamOrFail(teamId, matchId);
        matchRepository.delete(match);
    }

    private Match findOfTeamOrFail(Long teamId, Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id " + matchId));
        if (!match.getTeam().getId().equals(teamId)) {
            throw new ResourceNotFoundException(
                    "Match " + matchId + " does not belong to team " + teamId);
        }
        return match;
    }

}
