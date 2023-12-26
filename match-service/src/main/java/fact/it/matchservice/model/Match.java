package fact.it.matchservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name= "game")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private String date;

    @OneToOne(cascade = CascadeType.PERSIST)
    private TeamPerformance homeTeamPerformance;

    @OneToOne(cascade = CascadeType.PERSIST)
    private TeamPerformance awayTeamPerformance;

}
