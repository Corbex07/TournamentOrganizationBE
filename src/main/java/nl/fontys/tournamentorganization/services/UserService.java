package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.ApiMessage;
import nl.fontys.tournamentorganization.DTOs.User.UserRegistrationRequestDTO;
import nl.fontys.tournamentorganization.Repositories.Jpa.JpaUserRepository;
import nl.fontys.tournamentorganization.models.Users;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiMessage registerUser(UserRegistrationRequestDTO request) {

        if (userRepository.existsByUsername(request.username())) {
            return new ApiMessage(
                    409,
                    "Username is already in use",
                    null
            );
        }

        if (userRepository.existsByEmail(request.email())) {
            return new ApiMessage(
                    409,
                    "Email is already in use",
                    null
            );
        }

        Users user = new Users();

        user.setUsername(request.username());
        user.setEmail(request.email());
//        user.setPasswordHash(
//                passwordEncoder.encode(request.password())


        Users savedUser = userRepository.save(user);

        return new ApiMessage(
                201,
                "User registered successfully",
                savedUser
        );
    }

}
