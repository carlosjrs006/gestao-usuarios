package com.br.sistema.gestaousuarios.domain.dto;

import com.br.sistema.gestaousuarios.domain.TypeUser;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class UserDto {

    private Long id;
    private String login;
    private String email;
    private String phone;
    private String password;

}
