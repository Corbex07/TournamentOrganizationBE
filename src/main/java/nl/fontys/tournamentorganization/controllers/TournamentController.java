package nl.fontys.tournamentorganization.controllers;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<Tournament> all() {
        return tournamentService.getAllTournaments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTournament(@RequestBody TournamentDTO tournamentDTO) {
        tournamentService.saveTournament(tournamentDTO);
    }

    @GetMapping("/{id}")
    public void getTournamentById (@PathVariable Long id) {
        tournamentService.getTournamentById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
    }

    @PutMapping("/{id}")
    public void updateTournament(
            @PathVariable Long id,
            @RequestBody TournamentDTO tournamentDTO) {

        tournamentService.updateTournament(id, tournamentDTO);
    }
}
