package kaloyan.state_population_backend.scheduledTasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import kaloyan.state_population_backend.model.County;
import kaloyan.state_population_backend.service.CountyApiService;
import kaloyan.state_population_backend.service.CountyService;
import kaloyan.state_population_backend.service.MaterializedViewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@AllArgsConstructor
public class StatePopulationTableBuilder {

    private final CountyApiService apiService;

    private final CountyService countyService;

    private final MaterializedViewsService materializedViewsService;

    private static final String VIEW_NAME = "state_population_mv";
    private static final String QUERY = """
            SELECT SUM(population) as total_population, state
            FROM counties
            GROUP BY state
            """;

    private static final int PAGE_SIZE = 1000;

    private List<County> getAllCounties() throws JsonProcessingException {
        int pageNumber = 0;
        List<County> counties = new LinkedList<>();
        Page<County> page;
        do {
            page = apiService.fetchCountyAndPopulation(PageRequest.of(pageNumber, PAGE_SIZE));
            counties.addAll(page.getContent());
            pageNumber++;
        } while (!page.isLast());

        return counties;
    }

    @PostConstruct
    private void fetchDataSaveToDatabaseAndCreateMaterializedViews() throws JsonProcessingException {
        List<County> counties = getAllCounties();
        counties.forEach(countyService::upsertCounty);
        materializedViewsService.createMaterializedView(VIEW_NAME, QUERY);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void fetchDataSaveToDatabaseAndRefreshMaterializedViews() throws JsonProcessingException {
        List<County> counties = getAllCounties();
        counties.forEach(countyService::upsertCounty);
        materializedViewsService.refreshMaterializedView(VIEW_NAME);
    }
}
