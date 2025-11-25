package com.ev2.muebleria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ev2.muebleria")
@EnableJpaRepositories(basePackages = "com.ev2.muebleria.Repositorios")
@EntityScan(basePackages = "com.ev2.muebleria.Modelos")
public class MuebleriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuebleriaApplication.class, args);
	}

}
