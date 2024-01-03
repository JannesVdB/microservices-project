package fact.it.clubservice.service;

import fact.it.clubservice.dto.*;
import fact.it.clubservice.model.Club;
import fact.it.clubservice.model.Player;
import fact.it.clubservice.model.Squad;
import fact.it.clubservice.repository.ClubRepository;
import fact.it.clubservice.repository.PlayerRepository;
import fact.it.clubservice.repository.SquadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository clubRepository;
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;
    private final WebClient webClient;

    @Value("${playerservice.baseurl}")
    private String playerServiceBaseUrl;

    public void createClub(ClubRequest clubRequest) {
        Club club = Club.builder()
                .skuCode(clubRequest.getSkuCode())
                .name(clubRequest.getName())
                .foundingDate(clubRequest.getFoundingDate())
                .location(clubRequest.getLocation())
                .build();

        clubRepository.save(club);
    }

    public ClubResponse getClubBySkuCode(String skuCode) {
        return mapToClubResponse(clubRepository.findBySkuCode(skuCode));
    }

    public List<ClubResponse> getAllClubsBySkuCode(List<String> skuCodes) {
        List<Club> clubList = clubRepository.findBySkuCodeIn(skuCodes);

        return clubList.stream().map(this::mapToClubResponse).toList();
    }

    public void createSquad(String skuCodeClub, SquadRequest squadRequest) {
        Club club = clubRepository.findBySkuCode(skuCodeClub);

        if (club != null) {
            Squad squad = new Squad();
            squad.setClub(club);
            squad.setSkuCode(squadRequest.getSkuCode());
            squad.setFormation(squadRequest.getFormation());

            club.setSquad(squad);

            squadRepository.save(squad);
            clubRepository.save(club);
        }
    }

    public void updateSquadInfo(Long squadId, SquadRequest squadRequest) {
        Optional<Squad> optionalSquad = squadRepository.findById(squadId);

        if (optionalSquad.isPresent()) {
            Squad updatedSquad = optionalSquad.get();
            updatedSquad.setSkuCode(squadRequest.getSkuCode());
            updatedSquad.setFormation(squadRequest.getFormation());

            squadRepository.save(updatedSquad);
        }
    }

    public void addPlayerToSquad(Long squadId, String skuCode) {
        Optional<Squad> optionalSquad = squadRepository.findById(squadId);

        if (optionalSquad.isPresent()) {
            Squad squad = optionalSquad.get();

            Player player = playerRepository.findBySkuCodePlayer(skuCode).orElseGet(() -> {
                PlayerResponse newPlayerResponse = webClient.get().uri("http://" + playerServiceBaseUrl +"/api/player",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCode).build())
                        .retrieve()
                        .bodyToMono(PlayerResponse.class)
                        .block();

                if (newPlayerResponse != null) {
                    Player newPlayer = mapToNewPlayer(newPlayerResponse);
                    return playerRepository.save(newPlayer);
                } else {
                    return null;
                }
            });

            if (player != null) {
                player.setSquad(squad);
                squad.getPlayers().add(player);

                squadRepository.save(squad);
            }
        }
    }

    public void removePlayerFromSquad(Long playerId) {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();

            playerRepository.delete(player);
        }
    }

    private ClubResponse mapToClubResponse(Club club) {
        return ClubResponse.builder()
                .id(club.getId())
                .skuCode(club.getSkuCode())
                .name(club.getName())
                .foundingDate(club.getFoundingDate())
                .location(club.getLocation())
                .squad(club.getSquad() != null ? mapToSquadDto(club.getSquad()) : null)
                .build();
    }

    private SquadResonse mapToSquadDto(Squad squad) {
        return SquadResonse.builder()
                .id(squad.getId())
                .skuCode(squad.getSkuCode())
                .formation(squad.getFormation())
                .players(squad.getPlayers().stream().map(this::mapToPlayerDto).toList())
                .build();
    }

    private PlayerDto mapToPlayerDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .skuCodePlayer(player.getSkuCodePlayer())
                .name(player.getName())
                .position(player.getPosition())
                .build();
    }

    private Player mapToNewPlayer(PlayerResponse playerResponse) {
        return Player.builder()
                .skuCodePlayer(playerResponse.getSkuCode())
                .name(playerResponse.getName())
                .position(playerResponse.getPosition())
                .build();
    }
}
