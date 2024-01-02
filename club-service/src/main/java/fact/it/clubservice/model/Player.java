package fact.it.clubservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCodePlayer;
    private String name;
    private String position;

    @ManyToOne
    private Squad squad;
}
