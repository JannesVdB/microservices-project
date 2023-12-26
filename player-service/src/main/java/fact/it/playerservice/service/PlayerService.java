package fact.it.playerservice.service;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public void createPlayer(PlayerRequest playerRequest) {
        Player player = Player.builder()
                .skuCode(playerRequest.getSkuCode())
                .name(playerRequest.getName())
                .dateOfBirth(playerRequest.getDateOfBirth())
                .nationality(playerRequest.getNationality())
                .position(playerRequest.getPosition())
                .build();

        playerRepository.save(player);
    }

    public void updatePlayer(PlayerRequest playerRequest, String id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);

        if (optionalPlayer.isPresent()) {
            Player updatedPlayer = optionalPlayer.get();

            updatedPlayer.setSkuCode(playerRequest.getSkuCode());
            updatedPlayer.setName(playerRequest.getName());
            updatedPlayer.setDateOfBirth(playerRequest.getDateOfBirth());
            updatedPlayer.setNationality(playerRequest.getNationality());
            updatedPlayer.setPosition(playerRequest.getPosition());

            playerRepository.save(updatedPlayer);
        }
    }

    public PlayerResponse getPlayerBySkuCode(String skuCode) {
        Player player = playerRepository.findBySkuCode(skuCode);

        return mapToPlayerResponse(player);
    }

    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return players.stream().map(this::mapToPlayerResponse).toList();
    }

    private PlayerResponse mapToPlayerResponse(Player player) {
        return PlayerResponse.builder()
                .id(player.getId())
                .skuCode(player.getSkuCode())
                .name(player.getName())
                .dateOfBirth(player.getDateOfBirth())
                .nationality(player.getNationality())
                .position(player.getPosition())
                .build();
    }
}
