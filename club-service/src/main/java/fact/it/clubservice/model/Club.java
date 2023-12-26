package fact.it.clubservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "club")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private String name;
    private String foundingDate;
    private String location;

    @OneToOne
    private Squad squad;
}
