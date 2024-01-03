package fact.it.matchservice.service;

import fact.it.matchservice.dto.*;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.model.TeamPerformance;
import fact.it.matchservice.repository.MatchRepository;
import fact.it.matchservice.repository.TeamPerformanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamPerformanceRepository teamPerformanceRepository;
    private final WebClient webClient;

    @Value("${clubservice.baseurl}")
    private String clubServiceBaseUrl;

    public void createMatch(MatchRequest matchRequest) {
        Match match = new Match();

        TeamPerformance homeTeamPerformance = mapToTeamPerformance(matchRequest.getHomeTeamPerformance());
        TeamPerformance awayTeamPerformance = mapToTeamPerformance(matchRequest.getAwayTeamPerformance());

        ClubResponse[] clubResponseList = webClient.get().uri("http://" + clubServiceBaseUrl + "/api/club/allBySkuCode",
                        uriBuilder -> uriBuilder.queryParam("skuCode", homeTeamPerformance.getSkuCodeClub())
                                .queryParam("skuCode", awayTeamPerformance.getSkuCodeClub()).build())
                .retrieve()
                .bodyToMono(ClubResponse[].class)
                .block();

        Map<String, String> skuCodeToClubNameMap = Arrays.stream(clubResponseList)
                .collect(Collectors.toMap(ClubResponse::getSkuCode, ClubResponse::getName));

        homeTeamPerformance.setClubName(skuCodeToClubNameMap.get(homeTeamPerformance.getSkuCodeClub()));
        awayTeamPerformance.setClubName(skuCodeToClubNameMap.get(awayTeamPerformance.getSkuCodeClub()));

        match.setSkuCode(matchRequest.getSkuCode());
        match.setDate(matchRequest.getDate());
        match.setHomeTeamPerformance(homeTeamPerformance);
        match.setAwayTeamPerformance(awayTeamPerformance);

        matchRepository.save(match);
    }

    public void updateTeamPerformance(TeamPerformanceDto teamPerformanceDto, Long id) {
        Optional<TeamPerformance> optionalTeamPerformance = teamPerformanceRepository.findById(id);

        if (optionalTeamPerformance.isPresent()) {
            TeamPerformance teamPerformance = optionalTeamPerformance.get();

            teamPerformance.setSkuCode(teamPerformanceDto.getSkuCode());
            teamPerformance.setClubName(teamPerformanceDto.getClubName());
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
                teamPerformance.getClubName(),
                teamPerformance.getGoalsScored()
        );
    }

    private TeamPerformance mapToTeamPerformance(TeamPerformanceDto teamPerformanceDto) {
        TeamPerformance teamPerformance = new TeamPerformance();

        teamPerformance.setSkuCode(teamPerformanceDto.getSkuCode());
        teamPerformance.setSkuCodeClub(teamPerformanceDto.getSkuCodeClub());
        teamPerformance.setGoalsScored(teamPerformanceDto.getGoalsScored());

        return teamPerformance;
    }
}
