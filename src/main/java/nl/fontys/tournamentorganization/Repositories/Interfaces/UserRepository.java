package nl.fontys.tournamentorganization.Repositories.Interfaces;

import nl.fontys.tournamentorganization.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.Domains.UserDomain;

import java.util.Optional;

public interface UserRepository {

    Optional<UserDomain> findById(Long id);
}
