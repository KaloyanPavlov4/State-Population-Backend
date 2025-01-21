package kaloyan.state_population_backend.service;

import java.util.List;
import kaloyan.state_population_backend.model.StatePopulation;

public interface StatePopulationService {

    List<StatePopulation> getAllStatePopulations();

    StatePopulation getPopulationByState(String state);
}
