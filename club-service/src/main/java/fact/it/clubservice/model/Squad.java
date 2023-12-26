package fact.it.clubservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "squad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private String formation;

    @OneToOne(mappedBy = "squad")
    private Club club;

    @OneToMany(mappedBy = "squad")
    private List<Player> players;
}
