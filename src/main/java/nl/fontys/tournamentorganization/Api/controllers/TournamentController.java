package nl.fontys.tournamentorganization.Api.controllers;

import jakarta.validation.Valid;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.bll.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/V1/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {

        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(
                tournamentService.getAllTournaments()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<?> allActive() {
        return ResponseEntity.ok(
                tournamentService.getAllOpenRegistrationTournaments()
        );
    }

    @PostMapping
    public ResponseEntity<?> createTournament(
            @Valid @RequestBody CreateTournamentRequestDTO request) {

        tournamentService.createTournament(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentResponseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournament(@PathVariable Long id) {

        tournamentService.deleteTournament(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTournament(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTournamentRequestDTO updateTournamentRequestDTO) {

        tournamentService.updateTournament(id, updateTournamentRequestDTO);

        return ResponseEntity.noContent().build();
    }
}
