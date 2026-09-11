package nl.fontys.tournamentorganization.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
//public abstract class TournamentRepository implements ITournamentRepository {
//
//}
