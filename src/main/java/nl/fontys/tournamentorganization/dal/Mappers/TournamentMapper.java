package nl.fontys.tournamentorganization.dal.Mappers;

import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.dal.models.Tournament;
import org.springframework.stereotype.Component;

@Component
public class TournamentMapper {

    private final UserMapper userMapper;

    public TournamentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TournamentDomain toDomain(Tournament entity) {
        return new TournamentDomain(
                entity.getId(),
                entity.getName(),
                userMapper.toDomain(entity.getOrganiser()),
                entity.getMaxCapacity(),
                entity.getRegistrationDeadline(),
                entity.getStartDate(),
                entity.getRoundDurationHours()
        );
    }

    public Tournament toEntity(TournamentDomain domain) {
        Tournament entity = new Tournament(
                domain.getName(),
                userMapper.toEntity(domain.getOrganiser()),
                domain.getMaxCapacity(),
                domain.getRegistrationDeadline(),
                domain.getStartDate(),
                domain.getRoundDurationHours()
        );

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}