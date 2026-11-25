package nl.fontys.tournamentorganization.IntegrationTest;

import jakarta.transaction.Transactional;
import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.bll.services.TournamentService;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.TournamentRepository;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TournamentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void shouldCreateTournament() throws Exception {

        // Arrange

        UserDomain user = new UserDomain(1L,"testorganizer","organizer@test.com", "password");

        UserDomain savedUser = userRepository.save(user);

        Long organiserId = savedUser.getId();

        String requestJson = """
                {
                    "name": "Test Tournament",
                    "maxCapacity": 8,
                    "registrationDeadline": "%s",
                    "startDate": "%s",
                    "roundDurationHours": 2
                }
                """.formatted(
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(14)
        );

        // Act
        mockMvc.perform(post("/api/V1/tournaments")
                        .with(jwt().jwt(token ->
                                token.subject(organiserId.toString())))
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(requestJson))
                .andExpect(status().isCreated());
        // Assert
        assertTrue(
                tournamentRepository.findAll()
                        .stream()
                        .anyMatch(t -> t.getName().equals("Test Tournament"))
        );
    }
}