package fact.it.matchservice.repository;

import fact.it.matchservice.model.Match;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MatchRepository extends JpaRepository<Match, Long> {
    Match findBySkuCode(String skuCode);

        List<Match> findByHomeTeamPerformanceSkuCodeClubOrAwayTeamPerformanceSkuCodeClub(String skuCodeHome, String skuCodeAway);
}
