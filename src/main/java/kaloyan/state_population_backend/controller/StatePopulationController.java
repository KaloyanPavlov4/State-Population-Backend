package kaloyan.state_population_backend.controller;

import kaloyan.state_population_backend.model.StatePopulation;
import kaloyan.state_population_backend.service.StatePopulationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/state-populations")
@AllArgsConstructor
public class StatePopulationController {

    private final StatePopulationService statePopulationService;

    @GetMapping
    public List<StatePopulation> getAllStatePopulations() {
        return statePopulationService.getAllStatePopulations();
    }

    @GetMapping("/{stateName}")
    public StatePopulation getPopulationByState(@PathVariable String stateName) {
        return statePopulationService.getPopulationByState(stateName);
    }
}
