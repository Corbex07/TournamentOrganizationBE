package nl.fontys.tournamentorganization.dal.Mappers;

import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.dal.Entities.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDomain toDomain(Users entity) {
        return new UserDomain(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash()
        );
    }

    public Users toEntity(UserDomain domain) {
        Users entity = new Users(
                domain.getUsername(),
                domain.getEmail(),
                domain.getPasswordHash()
        );

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        return entity;
    }
}