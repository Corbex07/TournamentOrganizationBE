package nl.fontys.tournamentorganization.Config;

import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.interfaces.IUserRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
                "Weekend Warriors Cup",
                organiser2,
                16,
                LocalDateTime.of(2027, 9, 25, 18, 0),
                LocalDateTime.of(2027, 9, 26, 18, 0),
                48
        );

        Tournament tournament3 = new Tournament(
                "Benelux Amateur Open",
                organiser1,
                8,
                LocalDateTime.of(2027, 10, 2, 20, 0),
                LocalDateTime.of(2027, 10, 3, 14, 0),
                24
        );

        Tournament tournament4 = new Tournament(
                "Friday Night Valorant",
                organiser2,
                8,
                LocalDateTime.of(2027, 10, 8, 18, 0),
                LocalDateTime.of(2027, 10, 8, 20, 0),
                24
        );

        Tournament tournament5 = new Tournament(
                "Autumn Clash",
                organiser1,
                16,
                LocalDateTime.of(2027, 10, 15, 23, 59),
                LocalDateTime.of(2027, 10, 17, 13, 0),
                48
        );

        Tournament tournament6 = new Tournament(
                "Fontys Esports Championship",
                organiser2,
                16,
                LocalDateTime.of(2027, 10, 22, 18, 0),
                LocalDateTime.of(2027, 10, 23, 12, 0),
                48
        );

        Tournament tournament7 = new Tournament(
                "Halloween Showdown",
                organiser1,
                8,
                LocalDateTime.of(2027, 10, 29, 20, 0),
                LocalDateTime.of(2027, 10, 31, 18, 0),
                24
        );

        Tournament tournament8 = new Tournament(
                "Winter Qualifier",
                organiser2,
                16,
                LocalDateTime.of(2027, 11, 12, 23, 59),
                LocalDateTime.of(2027, 11, 14, 14, 0),
                48
        );

        tournamentRepository.saveAll(List.of(
                tournament1,
                tournament2,
                tournament3,
                tournament4,
                tournament5,
                tournament6,
                tournament7,
                tournament8
        ));
    }
}
