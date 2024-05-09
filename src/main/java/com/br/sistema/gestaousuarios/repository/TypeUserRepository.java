package com.br.sistema.gestaousuarios.repository;

import com.br.sistema.gestaousuarios.domain.TypeUser;
import com.br.sistema.gestaousuarios.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeUserRepository extends JpaRepository<TypeUser, Long> {

    TypeUser findByNome(String nome);
}
