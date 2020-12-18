package br.com.frwk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "br.com.frwk.*")

public class BackEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEngineApplication.class, args);
	}

}
