package fact.it.matchservice.repository;

import fact.it.matchservice.model.TeamPerformance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TeamPerformanceRepository extends JpaRepository<TeamPerformance, Long> {
}
