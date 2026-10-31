package nl.fontys.tournamentorganization.dal.Repositories.Implementations;

import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.dal.Mappers.TournamentMapper;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.TournamentRepository;
import nl.fontys.tournamentorganization.dal.Repositories.Jpa.JpaTournamentRepository;
import nl.fontys.tournamentorganization.dal.Entities.Tournament;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TournamentRepositoryImpl implements TournamentRepository {

    private final JpaTournamentRepository jpaRepository;

    private final TournamentMapper tournamentMapper;

    public TournamentRepositoryImpl(
            JpaTournamentRepository jpaRepository,
            TournamentMapper tournamentMapper
    ) {
        this.jpaRepository = jpaRepository;
        this.tournamentMapper = tournamentMapper;
    }


    @Override
    public Optional<TournamentDomain> findById(Long id) {
        return jpaRepository.findById(id)
                .map(tournamentMapper::toDomain);
    }

    @Override
    public List<TournamentDomain> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(tournamentMapper::toDomain)
                .toList();
    }

    @Override
    public List<TournamentDomain> findByRegistrationDeadlineAfter(LocalDateTime dateTime) {
        return jpaRepository.findByRegistrationDeadlineAfter(dateTime)
                .stream()
                .map(tournamentMapper::toDomain)
                .toList();
    }

    @Override
    public TournamentDomain save(TournamentDomain tournament) {
        Tournament entity = tournamentMapper.toEntity(tournament);

        Tournament saved = jpaRepository.save(entity);

        return tournamentMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
