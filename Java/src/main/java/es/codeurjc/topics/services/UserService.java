package es.codeurjc.topics.services;

import java.util.Collection;

import es.codeurjc.topics.dtos.requests.UpdateUserEmailRequestDto;
import es.codeurjc.topics.dtos.requests.UserRequestDto;
import es.codeurjc.topics.dtos.responses.UserResponseDto;

public interface UserService {

    Collection<UserResponseDto> findAll();

    UserResponseDto save(UserRequestDto userRequestDto);

    UserResponseDto findById(Long userId);

    UserResponseDto updateEmail(Long userId, UpdateUserEmailRequestDto updateUserEmailRequestDto);

    UserResponseDto delete(Long userId);

}
