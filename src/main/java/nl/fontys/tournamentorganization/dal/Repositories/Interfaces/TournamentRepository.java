package nl.fontys.tournamentorganization.dal.Repositories.Interfaces;

import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TournamentRepository {

    Optional<TournamentDomain> findById(Long id);

    List<TournamentDomain> findAll();

    List<TournamentDomain> findByRegistrationDeadlineAfter(LocalDateTime dateTime);

    TournamentDomain save(TournamentDomain tournament);

    void deleteById(Long id);
}