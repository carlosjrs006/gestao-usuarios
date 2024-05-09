package com.br.sistema.gestaousuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestaoUsuariosApplication {

	public static void main(String[] args) {
		String encode = new BCryptPasswordEncoder().encode("123");
		System.out.println("SEnha: " + encode);
		SpringApplication.run(GestaoUsuariosApplication.class, args);
	}

}
