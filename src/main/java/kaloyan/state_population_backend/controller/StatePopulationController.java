package kaloyan.state_population_backend.Controller;

import kaloyan.state_population_backend.model.StatePopulation;
import kaloyan.state_population_backend.service.StatePopulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/state-populations")
public class StatePopulationController {

    private final StatePopulationService statePopulationService;

    public StatePopulationController(StatePopulationService statePopulationService) {
        this.statePopulationService = statePopulationService;
    }

    @GetMapping
    public List<StatePopulation> getAllStatePopulations() {
        return statePopulationService.getAllStatePopulations();
    }

    @GetMapping("/{stateName}")
    public StatePopulation getPopulationByState(@PathVariable String stateName) {
        return statePopulationService.getPopulationByState(stateName);
    }
}
