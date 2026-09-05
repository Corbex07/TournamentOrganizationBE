package nl.fontys.tournamentorganization.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

    @Override
    public Optional<Tournament> getTournamentById(Long id) {
        Tournament tournament = entityManager.find(Tournament.class, id);

        return Optional.ofNullable(tournament);
    }

    @Override
    @Transactional
    public Tournament saveTournament(Tournament tournament) {
        return entityManager.merge(tournament);
    }

    @Override
    @Transactional
    public void deleteTournament(Long id) {
        Tournament tournament = entityManager.find(Tournament.class, id);

        if (tournament != null) {
            entityManager.remove(tournament);
        }
    }
}
