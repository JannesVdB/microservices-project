package fact.it.clubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponse {
    private String id;
    private String skuCode;
    private String name;
    private String dateOfBirth;
    private String nationality;
    private String position;
}
