package nl.fontys.tournamentorganization.Api.Mappers;

import nl.fontys.tournamentorganization.Api.DTOs.User.UserPrivateResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserResponseDTO;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponseDTO toResponseDto(UserDomain user);

    UserPrivateResponseDTO toPrivateResponseDto(UserDomain user);

}

