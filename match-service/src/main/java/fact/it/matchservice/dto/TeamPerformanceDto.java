package fact.it.matchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamPerformanceDto {
    private Long id;
    private String skuCode;
    private String skuCodeClub;
    private int goalsScored;
}
