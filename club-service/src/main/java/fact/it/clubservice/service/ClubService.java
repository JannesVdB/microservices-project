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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository clubRepository;
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;

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
            Squad squad = optionalSquad.get();
            squad.setSkuCode(squadRequest.getSkuCode());
            squad.setFormation(squadRequest.getFormation());
        }
    }

    public void addPlayerToSquad(Long squadId, String skuCode) {
        Optional<Squad> optionalSquad = squadRepository.findById(squadId);

        if (optionalSquad.isPresent()) {
            Squad squad = optionalSquad.get();

            Player player = playerRepository.findBySkuCodePlayer(skuCode).orElseGet(() -> {
                Player newPlayer = new Player();
                newPlayer.setSkuCodePlayer(skuCode);

                return playerRepository.save(newPlayer);
            });

            player.setSquad(squad);
            squad.getPlayers().add(player);

            squadRepository.save(squad);
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
                .build();
    }
}
