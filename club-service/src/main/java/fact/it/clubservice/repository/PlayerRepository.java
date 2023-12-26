package fact.it.clubservice.repository;

import fact.it.clubservice.model.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findBySkuCodePlayer(String skuCode);
}
