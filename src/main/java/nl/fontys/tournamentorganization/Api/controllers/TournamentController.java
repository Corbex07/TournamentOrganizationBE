package nl.fontys.tournamentorganization.Api.controllers;

import jakarta.validation.Valid;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.bll.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


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
            @Valid @RequestBody CreateTournamentRequestDTO request,
            @AuthenticationPrincipal Jwt jwt) {

        Long organiserId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));
        tournamentService.createTournament(request, organiserId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentResponseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournament(@PathVariable Long id,
                                              @AuthenticationPrincipal Jwt jwt) {

        Long organiserId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        tournamentService.deleteTournament(id, organiserId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTournament(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTournamentRequestDTO updateTournamentRequestDTO,
            @AuthenticationPrincipal Jwt jwt) {

        Long organiserId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        tournamentService.updateTournament(id, updateTournamentRequestDTO, organiserId );

        return ResponseEntity.noContent().build();
    }
}
