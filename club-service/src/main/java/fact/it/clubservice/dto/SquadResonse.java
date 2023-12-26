package fact.it.clubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SquadResonse {
    private Long id;
    private String skuCode;
    private String formation;
    private List<PlayerDto> players;
}
