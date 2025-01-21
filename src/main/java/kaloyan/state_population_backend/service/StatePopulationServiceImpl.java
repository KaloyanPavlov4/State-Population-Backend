package kaloyan.state_population_backend.service;

import kaloyan.state_population_backend.model.StatePopulation;
import kaloyan.state_population_backend.repository.StatePopulationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatePopulationServiceImpl implements StatePopulationService{

    private final StatePopulationRepository statePopulationRepository;

    public StatePopulationServiceImpl(StatePopulationRepository statePopulationRepository) {
        this.statePopulationRepository = statePopulationRepository;
    }

    @Override
    public List<StatePopulation> getAllStatePopulations() {
        return statePopulationRepository.findAllStatePopulations();
    }

    @Override
    public StatePopulation getPopulationByState(String state) {
        return statePopulationRepository.findByStateName(state);
    }
}
