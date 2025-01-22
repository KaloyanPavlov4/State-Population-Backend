package kaloyan.state_population_backend.repository;

import kaloyan.state_population_backend.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends JpaRepository<County, Integer> {

    County findByStateAndName(String state, String name);
}
