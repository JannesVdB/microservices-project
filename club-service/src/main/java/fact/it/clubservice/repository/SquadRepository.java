package fact.it.clubservice.repository;

import fact.it.clubservice.model.Squad;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface SquadRepository extends JpaRepository<Squad, Long> {
}
