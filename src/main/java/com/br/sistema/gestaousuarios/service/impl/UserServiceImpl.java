package com.br.sistema.gestaousuarios.service.impl;

import com.br.sistema.gestaousuarios.domain.TypeUser;
import com.br.sistema.gestaousuarios.domain.User;
import com.br.sistema.gestaousuarios.domain.dto.UserDto;
import com.br.sistema.gestaousuarios.domain.dto.UserRequestDto;
import com.br.sistema.gestaousuarios.infra.security.TokenService;
import com.br.sistema.gestaousuarios.repository.TypeUserRepository;
import com.br.sistema.gestaousuarios.repository.UserRepository;
import com.br.sistema.gestaousuarios.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TypeUserRepository typeUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return mapperReturnUserDto(users);
    }

    @Override
    public User createNewUser(UserRequestDto userRequestDto) {
        verifyEmailAndLoginExists(userRequestDto);

        TypeUser typeUser = verifyTypeUserExists(userRequestDto);

        return saveNewUser(userRequestDto,typeUser);
    }

    @Override
    public UserDto getByIdUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado!!"));

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(Objects.requireNonNullElse(user.getEmail(),""))
                .phone(Objects.requireNonNullElse(user.getPhone(),""))
                .password(user.getPassword())
                .build();
    }

    @Override
    @Transactional
    public void deleteByIdUsers(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado!!"));
        userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public User updateByIdUsers(Long id, UserRequestDto data) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        if (data.getEmail() != null) {
            existingUser.setEmail(data.getEmail());
        }
        if (data.getLogin() != null) {
            existingUser.setLogin(data.getLogin());
        }
        if (data.getPhone() != null) {
            existingUser.setPhone(data.getPhone());
        }
        if (data.getPassword() != null) {
            existingUser.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
        }

        if (data.getTipoUsuario() != null) {
            TypeUser typeUser = verifyTypeUserExists(data);
            existingUser.setTipoUsuario(typeUser);
        }

        return userRepository.save(existingUser);
    }

    private User saveNewUser(UserRequestDto userRequestDto, TypeUser typeUser){
        User build = User.builder()
                .email(Objects.requireNonNullElse(userRequestDto.getEmail(), ""))
                .phone(Objects.requireNonNullElse(userRequestDto.getPhone(), ""))
                .tipoUsuario(typeUser)
                .login(Objects.requireNonNullElse(userRequestDto.getLogin(), ""))
                .password(new BCryptPasswordEncoder().encode(userRequestDto.getPassword()))
                .build();

        return userRepository.save(build);

    }

    private TypeUser verifyTypeUserExists(UserRequestDto userRequestDto) {
        TypeUser typeUser = typeUserRepository.findByNome(userRequestDto.getTipoUsuario());

        if (Objects.isNull(typeUser)) {
            TypeUser newTypeUser = new TypeUser(userRequestDto.getTipoUsuario());
            return typeUserRepository.save(newTypeUser);
        }

        return typeUser;
    }

    private void verifyEmailAndLoginExists(UserRequestDto userRequestDto) {
        UserDetails login = userRepository.findByLogin(userRequestDto.getLogin());
        if(!Objects.isNull(login)){
            throw new RuntimeException("Login ja existente!!");
        }
        User userByEmail = userRepository.findByEmail(userRequestDto.getEmail());
        if(!Objects.isNull(userByEmail)){
         throw new RuntimeException("Email ja existente!!");
        }


    }

    private List<UserDto> mapperReturnUserDto(List<User> users){
        return users.stream().map(user -> {
            return UserDto.builder()
                   .id(user.getId())
                   .login(user.getLogin())
                   .email(Objects.requireNonNullElse(user.getEmail(),""))
                   .phone(Objects.requireNonNullElse(user.getPhone(),""))
                   .build();
        }).toList();

    }
}
