package nl.fontys.tournamentorganization.bll.services;

import nl.fontys.tournamentorganization.Api.DTOs.User.UserPrivateResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserUpdateRequestDTO;
import nl.fontys.tournamentorganization.Api.Exceptions.UserNotFoundException;
import nl.fontys.tournamentorganization.Api.Mappers.UserDtoMapper;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public UserPrivateResponseDTO updateUser(
            Long userId,
            UserUpdateRequestDTO dto
    ) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (dto.username() == null || dto.username().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        user.setUsername(dto.username().trim());

        UserDomain updatedUser = userRepository.save(user);

        return userDtoMapper.toPrivateResponseDto(updatedUser);
    }

    public UserResponseDTO getUserById(Long userId) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userDtoMapper.toResponseDto(user);
    }

    public UserPrivateResponseDTO getMe(Long userId) {
        UserDomain user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userDtoMapper.toPrivateResponseDto(user);
    }




}
