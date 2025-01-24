package kaloyan.state_population_backend.repository;

import kaloyan.state_population_backend.model.StatePopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatePopulationRepository extends JpaRepository<StatePopulation, String> {

    StatePopulation findByStateIgnoreCase(String stateName);
}
