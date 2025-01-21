package kaloyan.state_population_backend.scheduledTasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import kaloyan.state_population_backend.model.County;
import kaloyan.state_population_backend.service.CountyApiService;
import kaloyan.state_population_backend.service.CountyServiceImpl;
import kaloyan.state_population_backend.service.MaterializedViewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatePopulationTableBuilder {

    @Autowired
    CountyApiService apiService;

    @Autowired
    MaterializedViewsServiceImpl materializedViewsServiceImpl;

    @Autowired
    CountyServiceImpl countyService;

    private final String viewName = "state_population_mv";
    private final String query = """
            SELECT SUM(population) as total_population, state
            FROM counties
            GROUP BY state
            """;


    @PostConstruct
    public void fetchDataSaveToDatabaseAndCreateMaterializedViews() throws JsonProcessingException {
        List<County> counties = apiService.fetchCountyAndPopulation();
        counties.forEach(county -> countyService.upsertCounty(county));
        materializedViewsServiceImpl.createMaterializedView(viewName, query);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void fetchDataSaveToDatabaseAndRefreshMaterializedViews() throws JsonProcessingException {
        List<County> counties = apiService.fetchCountyAndPopulation();
        counties.forEach(county -> countyService.upsertCounty(county));
        materializedViewsServiceImpl.refreshMaterializedView(viewName);
    }
}
