package kaloyan.state_population_backend.service;

public interface MaterializedViewsService {

    int createMaterializedView(String viewName, String query);

    int refreshMaterializedView(String viewName);
}
