package kaloyan.state_population_backend.repository;

import kaloyan.state_population_backend.model.StatePopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatePopulationRepository extends JpaRepository<StatePopulation, String> {

    @Query(value = "SELECT * FROM state_population_mv", nativeQuery = true)
    List<StatePopulation> findAllStatePopulations();

    @Query(value = "SELECT * FROM state_population_mv WHERE state = :stateName", nativeQuery = true)
    StatePopulation findByStateName(String stateName);
}
