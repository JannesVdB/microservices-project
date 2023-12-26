package fact.it.matchservice.controller;

import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.dto.TeamPerformanceDto;
import fact.it.matchservice.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createMatch(@RequestBody MatchRequest matchRequest) {
        matchService.createMatch(matchRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMatch(@PathVariable Long id, @RequestBody TeamPerformanceDto teamPerformanceDto) {
        matchService.updateTeamPerformance(teamPerformanceDto, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MatchResponse getMatchBySkuCode(@RequestParam String skuCode) {
        return matchService.getMatchBySkuCode(skuCode);
    }

    @GetMapping("/club")
    @ResponseStatus(HttpStatus.OK)
    public List<MatchResponse> getMatchByClubSkuCode(@RequestParam String skuCode) {
        return matchService.getMatchByClubSkuCode(skuCode);
    }
}
