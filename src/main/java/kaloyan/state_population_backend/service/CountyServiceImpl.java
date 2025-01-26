package kaloyan.state_population_backend.service;

import kaloyan.state_population_backend.model.County;
import kaloyan.state_population_backend.repository.CountyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;

    public County upsertCounty(County county) {
        County existingCounty = countyRepository.findByStateAndName(county.getState(), county.getName());

        if (existingCounty != null) {
            existingCounty.setPopulation(county.getPopulation());
            return countyRepository.save(existingCounty);
        } else {
            return countyRepository.save(county);
        }
    }
}
