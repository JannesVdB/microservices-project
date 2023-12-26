package fact.it.matchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponse {
    private Long id;
    private String skuCode;
    private String date;
    private TeamPerformanceDto homeTeamPerformance;
    private TeamPerformanceDto awayTeamPerformance;
}
