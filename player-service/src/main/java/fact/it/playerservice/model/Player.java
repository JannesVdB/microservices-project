package fact.it.playerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Player {
    @Id
    private String id;
    private String skuCode;
    private String name;
    private String dateOfBirth;
    private String nationality;
    private String position;
}
