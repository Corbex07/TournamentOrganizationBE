package nl.fontys.tournamentorganization.Api.controllers;

import jakarta.validation.Valid;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserPrivateResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserUpdateRequestDTO;
import nl.fontys.tournamentorganization.bll.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.Objects;

@RestController
@RequestMapping("api/V1/users")
public class UserController {

    private static final Logger bleh = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserPrivateResponseDTO> getMe(@AuthenticationPrincipal Jwt jwt) {
        bleh.debug(jwt.toString());
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return ResponseEntity.ok(userService.getMe(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId ) {

        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserPrivateResponseDTO> updateMe(@AuthenticationPrincipal Jwt jwt,
                                                           @Valid @RequestBody UserUpdateRequestDTO requestDTO) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return ResponseEntity.ok(userService.updateUser(userId, requestDTO));
    }
}
