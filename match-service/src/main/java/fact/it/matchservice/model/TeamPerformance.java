package fact.it.matchservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "team_performances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private String skuCodeClub;
    private int goalsScored;
}
