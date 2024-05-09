package com.br.sistema.gestaousuarios.domain.dto;

import com.br.sistema.gestaousuarios.domain.TypeUser;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDto {

    private String login;
    private String password;
    private String email;
    private String phone;
    private String tipoUsuario;
}
