package fact.it.matchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
