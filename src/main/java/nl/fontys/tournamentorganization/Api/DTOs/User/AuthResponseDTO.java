package nl.fontys.tournamentorganization.Api.DTOs.User;

import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpHeaders;

public record AuthResponseDTO(
        String httpHeader,
        String cookie
) {
}
