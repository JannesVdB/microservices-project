package fact.it.playerservice.controller;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createPlayer(@RequestBody PlayerRequest playerRequest) {
        playerService.createPlayer(playerRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePlayer(@PathVariable String id, @RequestBody PlayerRequest playerRequest) {
        playerService.updatePlayer(playerRequest, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PlayerResponse getPlayerBySkyCode(@RequestParam String skuCode) {
        return playerService.getPlayerBySkuCode(skuCode);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponse> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}
