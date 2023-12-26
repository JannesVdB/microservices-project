package fact.it.clubservice.repository;

import fact.it.clubservice.model.Club;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findBySkuCode(String skuCode);
}
