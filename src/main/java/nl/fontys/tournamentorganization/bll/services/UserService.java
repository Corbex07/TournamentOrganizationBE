package nl.fontys.tournamentorganization.bll.services;

import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
