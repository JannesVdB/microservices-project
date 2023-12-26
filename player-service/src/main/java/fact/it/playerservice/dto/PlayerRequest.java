package fact.it.playerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequest {
    private String skuCode;
    private String name;
    private String dateOfBirth;
    private String nationality;
    private String position;
}