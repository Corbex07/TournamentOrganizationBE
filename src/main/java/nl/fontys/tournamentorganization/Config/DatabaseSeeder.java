package nl.fontys.tournamentorganization.Config;

import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.interfaces.IUserRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final ITournamentRepository tournamentRepository;

    public DatabaseSeeder(
            IUserRepository userRepository,
            ITournamentRepository tournamentRepository) {
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            return;
        }

        Users organiser1 = new Users(
                "organiser1",
                "organiser1@test.com",
                "password"
        );

        Users organiser2 = new Users(
                "organiser2",
                "organiser2@test.com",
                "password"
        );

        Users player1 = new Users(
                "player1",
                "player1@test.com",
                "password"
        );

        userRepository.save(organiser1);
        userRepository.save(organiser2);
        userRepository.save(player1);

        Tournament tournament1 = new Tournament(
                "Fontys Valorant Cup",
                organiser1,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                24
        );

        Tournament tournament2 = new Tournament(
                "Weekend Tournament",
                organiser2,
                16,
                LocalDateTime.of(2027, 9, 25, 18, 0),
                LocalDateTime.of(2027, 9, 26, 18, 0),
                48
        );

        tournamentRepository.save(tournament1);
        tournamentRepository.save(tournament2);
    }
}
