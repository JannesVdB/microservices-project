package fact.it.matchservice.service;

import fact.it.matchservice.dto.*;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.model.TeamPerformance;
import fact.it.matchservice.repository.MatchRepository;
import fact.it.matchservice.repository.TeamPerformanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamPerformanceRepository teamPerformanceRepository;

    public void createMatch(MatchRequest matchRequest) {
        Match match = new Match();

        match.setSkuCode(matchRequest.getSkuCode());
        match.setDate(matchRequest.getDate());

        TeamPerformance homeTeamPerformance = mapToTeamPerformance(matchRequest.getHomeTeamPerformance());
        TeamPerformance awayTeamPerformance = mapToTeamPerformance(matchRequest.getAwayTeamPerformance());

        match.setHomeTeamPerformance(homeTeamPerformance);
        match.setAwayTeamPerformance(awayTeamPerformance);

        matchRepository.save(match);
    }

    public void updateTeamPerformance(TeamPerformanceDto teamPerformanceDto, Long id) {
        Optional<TeamPerformance> optionalTeamPerformance = teamPerformanceRepository.findById(id);

        if (optionalTeamPerformance.isPresent()) {
            TeamPerformance teamPerformance = optionalTeamPerformance.get();

            teamPerformance.setSkuCode(teamPerformanceDto.getSkuCode());
            teamPerformance.setSkuCodeClub(teamPerformanceDto.getSkuCodeClub());
            teamPerformance.setGoalsScored(teamPerformanceDto.getGoalsScored());

            teamPerformanceRepository.save(teamPerformance);
        }
    }

    public MatchResponse getMatchBySkuCode(String skuCode) {
        Match match = matchRepository.findBySkuCode(skuCode);

        return mapToMatchResponse(match);
    }

    public List<MatchResponse> getMatchByClubSkuCode(String skuCode) {
        List<Match> matches = matchRepository.findByHomeTeamPerformanceSkuCodeClubOrAwayTeamPerformanceSkuCodeClub(skuCode, skuCode);

        return matches.stream().map(this::mapToMatchResponse).toList();
    }

    private MatchResponse mapToMatchResponse(Match match) {
        return MatchResponse.builder()
                .id(match.getId())
                .skuCode(match.getSkuCode())
                .date(match.getDate())
                .homeTeamPerformance(mapToTeamPerformanceDto(match.getHomeTeamPerformance()))
                .awayTeamPerformance(mapToTeamPerformanceDto(match.getAwayTeamPerformance()))
                .build();
    }

    private TeamPerformanceDto mapToTeamPerformanceDto(TeamPerformance teamPerformance) {
        return new TeamPerformanceDto(
                teamPerformance.getId(),
                teamPerformance.getSkuCode(),
                teamPerformance.getSkuCodeClub(),
                teamPerformance.getGoalsScored()
        );
    }

    private TeamPerformance mapToTeamPerformance(TeamPerformanceDto teamPerformanceDto) {
        TeamPerformance teamPerformance = new TeamPerformance();

        teamPerformance.setSkuCode( teamPerformanceDto.getSkuCode());
        teamPerformance.setSkuCodeClub(teamPerformanceDto.getSkuCodeClub());
        teamPerformance.setGoalsScored(teamPerformanceDto.getGoalsScored());

        return teamPerformance;
    }
}
