package nl.fontys.tournamentorganization.Api.controllers;

import jakarta.validation.Valid;
import nl.fontys.tournamentorganization.Api.DTOs.User.AuthResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserLoginRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserRegistrationRequestDTO;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.bll.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/V1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody UserRegistrationRequestDTO request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}