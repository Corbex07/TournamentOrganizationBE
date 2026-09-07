package nl.fontys.tournamentorganization.controllers;

import nl.fontys.tournamentorganization.Repositories.TournamentRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentRepository tournamentRepository;

    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    @GetMapping("/tournaments")
    List<Tournament> all() {
        return tournamentRepository.getAllTournaments();
    }

    @PostMapping("/tournaments/{id}")
    Tournament newTournament(@RequestBody Tournament newTournament) {
        return tournamentRepository.saveTournament(newTournament);
    }

    @GetMapping("/tournaments/{id}")
    Tournament one(@PathVariable Long id) {

        return tournamentRepository.getTournamentById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tournament not found"
                    )
                );
    }

    @DeleteMapping("/tournament/{id}")
    void deleteEmployee(@PathVariable Long id) {
        tournamentRepository.deleteTournament(id);
    }
}
