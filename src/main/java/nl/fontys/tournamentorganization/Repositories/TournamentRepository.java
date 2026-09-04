package nl.fontys.tournamentorganization.Repositories;

import jakarta.persistence.EntityManager;
import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TournamentRepository implements ITournamentRepository {

    private final EntityManager entityManager;

    public TournamentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return entityManager
                .createQuery("SELECT t FROM Tournament t", Tournament.class)
                .getResultList();
    }

    public Optional<Tournament> getTournamentById(Long id) {
        return Optional.empty();
    }

    public Tournament saveTournament(Tournament tournament) {
        return null;
    }

    public void deleteTournament(Long id) {

    }
}
