package nl.fontys.tournamentorganization.controllers;

import nl.fontys.tournamentorganization.DTOs.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.DTOs.TournamentResponseDTO;
import nl.fontys.tournamentorganization.DTOs.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V1/tournaments")
@CrossOrigin(origins = "http://localhost:5173")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {

        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<TournamentResponseDTO> all() {
        return tournamentService.getAllTournaments();
    }

    @GetMapping("/active")
    public List<TournamentResponseDTO> allActive() {
        return tournamentService.getAllOpenRegistrationTournaments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTournament(@RequestBody CreateTournamentRequestDTO createTournamentRequestDTO) {
        tournamentService.createTournament(createTournamentRequestDTO);
    }

    @GetMapping("/{id}")
    public TournamentResponseDTO getTournamentById (@PathVariable Long id) {
        return tournamentService.getTournamentResponseById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
    }

    @PutMapping("/{id}")
    public void updateTournament(
            @PathVariable Long id,
            @RequestBody UpdateTournamentRequestDTO updateTournamentRequestDTO) {

        tournamentService.updateTournament(id, updateTournamentRequestDTO);
    }
}
