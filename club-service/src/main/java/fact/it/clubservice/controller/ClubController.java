package fact.it.clubservice.controller;

import fact.it.clubservice.dto.ClubRequest;
import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.dto.SquadRequest;
import fact.it.clubservice.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createClub(@RequestBody ClubRequest clubRequest) {
        clubService.createClub(clubRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ClubResponse getClubBySkuCode(@RequestParam String skuCode) {
        return clubService.getClubBySkuCode(skuCode);
    }

    @GetMapping("/allBySkuCode")
    @ResponseStatus(HttpStatus.OK)
    public List<ClubResponse> getAllClubsBySkuCode(@RequestParam List<String> skuCode) {
        return  clubService.getAllClubsBySkuCode(skuCode);
    }

    @PostMapping("/squad")
    @ResponseStatus(HttpStatus.OK)
    public void createSquad(@RequestParam String skuCodeClub, @RequestBody SquadRequest squadRequest) {
        clubService.createSquad(skuCodeClub, squadRequest);
    }

    @PutMapping("/squad/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSquadInfo(@PathVariable Long id, @RequestBody SquadRequest squadRequest) {
        clubService.updateSquadInfo(id, squadRequest);
    }

    @PostMapping("/player/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addPlayerToSquad(@PathVariable Long id, @RequestParam String skuCode) {
        clubService.addPlayerToSquad(id, skuCode);
    }

    @DeleteMapping("/player/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removePlayerFromSquad(@PathVariable Long id) {
        clubService.removePlayerFromSquad(id);
    }
}
