package fact.it.clubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubResponse {
    private Long id;
    private String skuCode;
    private String name;
    private String foundingDate;
    private String location;
    private SquadResonse squad;
}
