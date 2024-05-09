package com.br.sistema.gestaousuarios.service;

import com.br.sistema.gestaousuarios.domain.User;
import com.br.sistema.gestaousuarios.domain.dto.UserDto;
import com.br.sistema.gestaousuarios.domain.dto.UserRequestDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUser();

    User createNewUser(UserRequestDto userRequestDto);

    UserDto getByIdUser(Long id);

    void deleteByIdUsers(Long id);

    User updateByIdUsers(Long id, UserRequestDto data);
}
