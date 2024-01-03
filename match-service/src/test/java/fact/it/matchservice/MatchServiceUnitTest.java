package fact.it.matchservice;

import fact.it.matchservice.dto.ClubResponse;
import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.dto.TeamPerformanceDto;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.model.TeamPerformance;
import fact.it.matchservice.repository.MatchRepository;
import fact.it.matchservice.repository.TeamPerformanceRepository;
import fact.it.matchservice.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceUnitTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TeamPerformanceRepository teamPerformanceRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(matchService, "clubServiceBaseUrl", "http://localhost:8081");
    }

    @Test
    void testCreateMatch() {
        MatchRequest matchRequest = new MatchRequest();
        matchRequest.setSkuCode("123");
        matchRequest.setDate("2023/12/25");

        TeamPerformanceDto homeTeamPerformanceDto = new TeamPerformanceDto();
        homeTeamPerformanceDto.setSkuCode("123");
        homeTeamPerformanceDto.setSkuCodeClub("33");
        homeTeamPerformanceDto.setGoalsScored(0);
        matchRequest.setHomeTeamPerformance(homeTeamPerformanceDto);

        TeamPerformanceDto awayTeamPerformanceDto = new TeamPerformanceDto();
        awayTeamPerformanceDto.setSkuCode("124");
        awayTeamPerformanceDto.setSkuCodeClub("34");
        awayTeamPerformanceDto.setGoalsScored(0);
        matchRequest.setAwayTeamPerformance(awayTeamPerformanceDto);

        ClubResponse clubResponse1 = new ClubResponse();
        clubResponse1.setSkuCode("255");
        clubResponse1.setName("Test club 1");

        ClubResponse clubResponse2 = new ClubResponse();
        clubResponse2.setSkuCode("256");
        clubResponse2.setName("Test club 2");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ClubResponse[].class)).thenReturn(Mono.just(new ClubResponse[]{clubResponse1, clubResponse2}));

        // Act
        matchService.createMatch(matchRequest);

        // Assert
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void testUpdateTeamPerformance() {
        // Arrange
        TeamPerformanceDto teamPerformanceDto = new TeamPerformanceDto();

        teamPerformanceDto.setSkuCode("234");
        teamPerformanceDto.setSkuCodeClub("20");
        teamPerformanceDto.setGoalsScored(3);
        teamPerformanceDto.setClubName("Test club");

        Long teamPerformanceId = 1L;

        when(teamPerformanceRepository.findById(teamPerformanceId)).thenReturn(Optional.of(new TeamPerformance()));

        // Act
        matchService.updateTeamPerformance(teamPerformanceDto, teamPerformanceId);

        // Assert
        verify(teamPerformanceRepository, times(1)).save(any(TeamPerformance.class));
    }

    @Test
    void testGetMatchBySkuCode() {
        // Arrange
        Match match = new Match();
        match.setId(1L);
        match.setSkuCode("123");
        match.setDate("2023/12/25");

        TeamPerformance homeTeamPerformance = new TeamPerformance();
        homeTeamPerformance.setSkuCode("234");
        homeTeamPerformance.setSkuCodeClub("20");
        homeTeamPerformance.setGoalsScored(3);
        homeTeamPerformance.setClubName("Test club");
        match.setHomeTeamPerformance(homeTeamPerformance);

        TeamPerformance awayTeamPerformance = new TeamPerformance();
        awayTeamPerformance.setSkuCode("123");
        awayTeamPerformance.setSkuCodeClub("21");
        awayTeamPerformance.setGoalsScored(2);
        awayTeamPerformance.setClubName("Test club 1");
        match.setAwayTeamPerformance(awayTeamPerformance);

        when(matchRepository.findBySkuCode("123")).thenReturn(match);

        // Act
        MatchResponse matchResponse = matchService.getMatchBySkuCode("123");

        // Assert
        assertEquals(1L, matchResponse.getId());
        assertEquals("123", matchResponse.getSkuCode());
        assertEquals("2023/12/25", matchResponse.getDate());
        assertEquals("234", matchResponse.getHomeTeamPerformance().getSkuCode());
        assertEquals("20", matchResponse.getHomeTeamPerformance().getSkuCodeClub());
        assertEquals(3, matchResponse.getHomeTeamPerformance().getGoalsScored());
        assertEquals("Test club", matchResponse.getHomeTeamPerformance().getClubName());
        assertEquals("123", matchResponse.getAwayTeamPerformance().getSkuCode());
        assertEquals("21", matchResponse.getAwayTeamPerformance().getSkuCodeClub());
        assertEquals(2, matchResponse.getAwayTeamPerformance().getGoalsScored());
        assertEquals("Test club 1", matchResponse.getAwayTeamPerformance().getClubName());

        verify(matchRepository, times(1)).findBySkuCode(match.getSkuCode());
    }

    @Test
    void testGetMatchByClubSkuCode() {
        // Arrange
        Match match1 = new Match();
        match1.setId(1L);
        match1.setSkuCode("123");
        match1.setDate("2023/12/25");

        TeamPerformance homeTeamPerformance = new TeamPerformance();
        homeTeamPerformance.setId(1L);
        homeTeamPerformance.setSkuCode("234");
        homeTeamPerformance.setSkuCodeClub("20");
        homeTeamPerformance.setGoalsScored(3);
        homeTeamPerformance.setClubName("Test club");
        match1.setHomeTeamPerformance(homeTeamPerformance);

        TeamPerformance awayTeamPerformance = new TeamPerformance();
        awayTeamPerformance.setId(2L);
        awayTeamPerformance.setSkuCode("123");
        awayTeamPerformance.setSkuCodeClub("21");
        awayTeamPerformance.setGoalsScored(2);
        awayTeamPerformance.setClubName("Test club 1");
        match1.setAwayTeamPerformance(awayTeamPerformance);

        Match match2 = new Match();
        match1.setId(2L);
        match1.setSkuCode("234");
        match1.setDate("2023/12/26");

        TeamPerformance homeTeamPerformance1 = new TeamPerformance();
        homeTeamPerformance1.setId(3L);
        homeTeamPerformance1.setSkuCode("235");
        homeTeamPerformance1.setSkuCodeClub("21");
        homeTeamPerformance1.setGoalsScored(3);
        homeTeamPerformance1.setClubName("Test club 1");
        match2.setHomeTeamPerformance(homeTeamPerformance1);

        TeamPerformance awayTeamPerformance1 = new TeamPerformance();
        awayTeamPerformance1.setId(4L);
        awayTeamPerformance1.setSkuCode("124");
        awayTeamPerformance1.setSkuCodeClub("20");
        awayTeamPerformance1.setGoalsScored(2);
        awayTeamPerformance1.setClubName("Test club");
        match2.setAwayTeamPerformance(awayTeamPerformance1);

        when(matchRepository.findByHomeTeamPerformanceSkuCodeClubOrAwayTeamPerformanceSkuCodeClub("20", "20"))
                .thenReturn(Arrays.asList(match1, match2));

        // Act
        List<MatchResponse> result = matchService.getMatchByClubSkuCode("20");

        // Assert
        assertEquals(2, result.size());

        verify(matchRepository, times(1)).findByHomeTeamPerformanceSkuCodeClubOrAwayTeamPerformanceSkuCodeClub("20", "20");
    }
}
