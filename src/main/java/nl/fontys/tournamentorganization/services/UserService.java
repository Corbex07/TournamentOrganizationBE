package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.ApiMessage;
import nl.fontys.tournamentorganization.DTOs.User.UserRegistrationRequestDTO;
import nl.fontys.tournamentorganization.Repositories.Interfaces.UserRepository;
import nl.fontys.tournamentorganization.Repositories.Jpa.JpaUserRepository;
import nl.fontys.tournamentorganization.models.Users;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
