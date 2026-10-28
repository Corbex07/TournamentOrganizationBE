package nl.fontys.tournamentorganization.dal.Repositories.Implementations;

import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.dal.Mappers.UserMapper;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import nl.fontys.tournamentorganization.dal.Repositories.Jpa.JpaUserRepository;
import nl.fontys.tournamentorganization.dal.models.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(
            JpaUserRepository jpaRepository,
            UserMapper userMapper
    ) {
        this.jpaRepository = jpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDomain> findById(Long id) {
        return jpaRepository.findById(id)
                .map(userMapper::toDomain);

    }

    @Override
    public Optional<UserDomain> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public UserDomain save(UserDomain user) {

        Users entity = userMapper.toEntity(user);

        Users savedEntity = jpaRepository.save(entity);

        return userMapper.toDomain(savedEntity);
    }
}
