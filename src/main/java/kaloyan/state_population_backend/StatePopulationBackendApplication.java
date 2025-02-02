package kaloyan.state_population_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StatePopulationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatePopulationBackendApplication.class, args);
	}
}
