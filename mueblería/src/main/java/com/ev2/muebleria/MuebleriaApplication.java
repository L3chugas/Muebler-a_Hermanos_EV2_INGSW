package com.ev2.muebleria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication(scanBasePackages = "com.ev2.muebleria")
@EnableJpaRepositories(basePackages = "com.ev2.muebleria.Repositorios")
@EntityScan(basePackages = "com.ev2.muebleria.Modelos")
public class MuebleriaApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(MuebleriaApplication.class, args);
		int port = context.getEnvironment().getProperty("local.server.port", Integer.class, 8080);
		System.out.println("\nProgram started successfully :)");
		System.out.println("Backend running at: http://localhost:" + port);

		// Escribir el puerto en un archivo env.js para el frontend
		try {
			Path frontendDir = null;
			// Intentar localizar la carpeta frontend en ubicaciones comunes
			if (Files.exists(Paths.get("../frontend"))) {
				frontendDir = Paths.get("../frontend");
			} else if (Files.exists(Paths.get("frontend"))) {
				frontendDir = Paths.get("frontend");
			}

			if (frontendDir != null) {
				String envPath = frontendDir.resolve("env.js").toAbsolutePath().normalize().toString();
				try (FileWriter writer = new FileWriter(envPath)) {
					writer.write("window.BACKEND_PORT = " + port + ";");
				}
				System.out.println("Frontend config updated at: " + envPath);
			} else {
				System.err.println("ADVERTENCIA: No se encontró la carpeta 'frontend'. Directorio actual: " + System.getProperty("user.dir"));
			}
		} catch (IOException e) {
			System.err.println("Could not update frontend config: " + e.getMessage());
		}
	}

}
