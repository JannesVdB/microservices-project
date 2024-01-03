package fact.it.playerservice;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import fact.it.playerservice.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceUnitTest {
    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void testCreatePlayer() {
        // Arrange
        PlayerRequest playerRequest = new PlayerRequest();

        playerRequest.setSkuCode("123");
        playerRequest.setName("Test player");
        playerRequest.setPosition("LW");
        playerRequest.setNationality("Belgian");
        playerRequest.setDateOfBirth("1996/12/25");

        // Act
        playerService.createPlayer(playerRequest);

        // Assert
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    public void testUpdatePlayer() {
        // Arrange
        PlayerRequest playerRequest = new PlayerRequest();

        playerRequest.setSkuCode("123");
        playerRequest.setName("Test player");
        playerRequest.setPosition("LW");
        playerRequest.setNationality("Belgian");
        playerRequest.setDateOfBirth("1996/12/25");

        String playerId = "1";

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(new Player()));

        // Act
        playerService.updatePlayer(playerRequest, playerId);

        // Assert
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testGetPlayerBySkuCode() {
        // Arrange
        Player player = new Player();

        player.setId("1");
        player.setSkuCode("123");
        player.setName("Test player");
        player.setPosition("LW");
        player.setNationality("Belgian");
        player.setDateOfBirth("1996/12/25");

        when(playerRepository.findBySkuCode("123")).thenReturn(player);

        // Act
        PlayerResponse playerResponse = playerService.getPlayerBySkuCode("123");

        // Assert
        assertEquals("1", playerResponse.getId());
        assertEquals("123", playerResponse.getSkuCode());
        assertEquals("Test player", playerResponse.getName());
        assertEquals("LW", playerResponse.getPosition());
        assertEquals("Belgian", playerResponse.getNationality());
        assertEquals("1996/12/25", playerResponse.getDateOfBirth());

        verify(playerRepository, times(1)).findBySkuCode(player.getSkuCode());
    }

    @Test
    void testGetAllPlayers() {
        // Arrange
        Player player = new Player();

        player.setId("1");
        player.setSkuCode("123");
        player.setName("Test player");
        player.setPosition("LW");
        player.setNationality("Belgian");
        player.setDateOfBirth("1996/12/25");

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player));

        // Act
        List<PlayerResponse> playerResponses = playerService.getAllPlayers();

        // Assert
        assertEquals(1, playerResponses.size());
        assertEquals("1", playerResponses.get(0).getId());
        assertEquals("123", playerResponses.get(0).getSkuCode());
        assertEquals("Test player", playerResponses.get(0).getName());
        assertEquals("LW", playerResponses.get(0).getPosition());
        assertEquals("Belgian", playerResponses.get(0).getNationality());
        assertEquals("1996/12/25", playerResponses.get(0).getDateOfBirth());

        verify(playerRepository, times(1)).findAll();
    }
}
