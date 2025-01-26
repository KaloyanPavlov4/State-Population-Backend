package kaloyan.state_population_backend.service;

import kaloyan.state_population_backend.model.County;

public interface CountyService {

    County upsertCounty(County county);
}
