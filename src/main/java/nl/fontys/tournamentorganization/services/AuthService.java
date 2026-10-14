package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.User.UserRegistrationRequestDTO;
import nl.fontys.tournamentorganization.Domains.UserDomain;
import nl.fontys.tournamentorganization.Repositories.Interfaces.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegistrationRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        if (userRepository.existsByUsername(requestDTO.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        String passwordHash = passwordEncoder.encode(requestDTO.password());

        UserDomain userDomain = new UserDomain(
                requestDTO.username(),
                requestDTO.email(),
                passwordHash
        );

        userRepository.save(userDomain);
    }


}
