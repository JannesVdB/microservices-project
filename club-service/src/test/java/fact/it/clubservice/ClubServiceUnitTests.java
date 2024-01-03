package fact.it.clubservice;

import fact.it.clubservice.dto.ClubRequest;
import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.dto.PlayerResponse;
import fact.it.clubservice.dto.SquadRequest;
import fact.it.clubservice.model.Club;
import fact.it.clubservice.model.Player;
import fact.it.clubservice.model.Squad;
import fact.it.clubservice.repository.ClubRepository;
import fact.it.clubservice.repository.PlayerRepository;
import fact.it.clubservice.repository.SquadRepository;
import fact.it.clubservice.service.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClubServiceUnitTests {
    @InjectMocks
    private ClubService clubService;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private SquadRepository squadRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(clubService, "playerServiceBaseUrl", "http://localhost:8080");
    }

    @Test
    public void testCreateClub() {
        // Arrange
        ClubRequest clubRequest = new ClubRequest();

        clubRequest.setSkuCode("123");
        clubRequest.setName("Test club");
        clubRequest.setLocation("Test location");
        clubRequest.setFoundingDate("2023/12/25");

        // Act
        clubService.createClub(clubRequest);

        // Assert
        verify(clubRepository, times(1)).save(any(Club.class));
    }

    @Test
    public void testGetClubBySkuCode() {
        // Arrange
        Club club = new Club();

        club.setId(1L);
        club.setSkuCode("123");
        club.setName("Test club");
        club.setLocation("Test location");
        club.setFoundingDate("2023/12/25");

        when(clubRepository.findBySkuCode("123")).thenReturn(club);

        // Act
        ClubResponse clubResponse = clubService.getClubBySkuCode("123");

        // Assert
        assertEquals(1L, clubResponse.getId());
        assertEquals("123", clubResponse.getSkuCode());
        assertEquals("Test club", clubResponse.getName());
        assertEquals("Test location", clubResponse.getLocation());
        assertEquals("2023/12/25", clubResponse.getFoundingDate());

        verify(clubRepository, times(1)).findBySkuCode(club.getSkuCode());
    }

    @Test
    public void testGetAllClubsBySkuCode() {
        // Arrange
        Club club = new Club();

        club.setId(1L);
        club.setSkuCode("123");
        club.setName("Test club");
        club.setLocation("Test location");
        club.setFoundingDate("2023/12/25");

        when(clubRepository.findBySkuCodeIn(Arrays.asList("123"))).thenReturn(Arrays.asList(club));

        // Act
        List<ClubResponse> clubResponses = clubService.getAllClubsBySkuCode(Arrays.asList("123"));

        // Assert
        assertEquals(1, clubResponses.size());
        assertEquals(1L, clubResponses.get(0).getId());
        assertEquals("123", clubResponses.get(0).getSkuCode());
        assertEquals("Test club", clubResponses.get(0).getName());
        assertEquals("Test location", clubResponses.get(0).getLocation());
        assertEquals("2023/12/25", clubResponses.get(0).getFoundingDate());

        verify(clubRepository, times(1)).findBySkuCodeIn(Arrays.asList(club.getSkuCode()));
    }

    @Test
    public void testCreateSquad() {
        // Arrange
        SquadRequest squadRequest = new SquadRequest();

        squadRequest.setSkuCode("123");
        squadRequest.setFormation("4-3-3");

        Club club = new Club();
        club.setId(1L);
        club.setSkuCode("123");
        club.setName("Test club");
        club.setLocation("Test location");
        club.setFoundingDate("2023/12/25");

        when(clubRepository.findBySkuCode("123")).thenReturn(club);

        // Act
        clubService.createSquad(club.getSkuCode(), squadRequest);

        // Assert
        verify(squadRepository, times(1)).save(any(Squad.class));
        verify(clubRepository, times(1)).save(any(Club.class));
    }

    @Test
    public void testUpdateSquadInfo() {
        // Arrange
        SquadRequest squadRequest = new SquadRequest();

        squadRequest.setSkuCode("123");
        squadRequest.setFormation("4-3-3");

        Long squadId = 1L;

        when(squadRepository.findById(squadId)).thenReturn(Optional.of(new Squad()));

        // Act
        clubService.updateSquadInfo(squadId, squadRequest);

        // Arrange
        verify(squadRepository, times(1)).save(any(Squad.class));
    }

    @Test
    void testAddPlayerToSquad() {
        // Arrange
        Squad squad = new Squad();

        Long squadId = 1L;
        squad.setId(squadId);
        squad.setSkuCode("123");
        squad.setFormation("4-3-3");
        squad.setPlayers(new ArrayList<>());

        PlayerResponse playerResponse = new PlayerResponse();

        playerResponse.setId("1");
        playerResponse.setSkuCode("25");
        playerResponse.setName("Test player");
        playerResponse.setPosition("LW");
        playerResponse.setNationality("Belgian");
        playerResponse.setDateOfBirth("1996/12/25");

        Player player = new Player();

        player.setId(1L);
        player.setSkuCodePlayer("25");
        player.setName("Test player");
        player.setPosition("LW");

        when(squadRepository.findById(squadId)).thenReturn(Optional.of(squad));
        when(playerRepository.findBySkuCodePlayer("25")).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PlayerResponse.class)).thenReturn(Mono.just(playerResponse));

        // Act
        clubService.addPlayerToSquad(squadId, "25");

        // Assert
        verify(playerRepository, times(1)).save(any(Player.class));
        verify(squadRepository, times(1)).save(any(Squad.class));
    }

    @Test
    void testRemovePlayerFromSquad() {
        // Arrange
        Long playerId = 1L;

        Player player = new Player();
        player.setId(playerId);
        player.setSkuCodePlayer("123");
        player.setName("Test player");
        player.setPosition("LW");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        // Act
        clubService.removePlayerFromSquad(playerId);

        // Assert
        verify(playerRepository, times(1)).delete(player);
    }
}
