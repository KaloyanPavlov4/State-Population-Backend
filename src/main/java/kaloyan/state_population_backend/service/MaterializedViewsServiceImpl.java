package kaloyan.state_population_backend.service;

import kaloyan.state_population_backend.repository.MaterializedViewsRepository;
import kaloyan.state_population_backend.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatePopulationMaterializedViewsService implements MaterializedViewsService {

    @Autowired
    private MaterializedViewsRepository materializedViewsRepository;

    private String createMaterializedViewSQL(String viewName, String state) {
        return String.format("""
                DO $$
                BEGIN
                    IF EXISTS (
                       SELECT 1
                       FROM pg_matviews
                       WHERE matviewname = '%s'
                    ) THEN
                        EXECUTE 'REFRESH MATERIALIZED VIEW CONCURRENTLY %s';
                    ELSE
                        EXECUTE $view$
                            CREATE MATERIALIZED VIEW %s AS
                            SELECT SUM(population) as total_population, state
                            FROM county
                            WHERE state = '%s'
                            GROUP BY state
                        $view$;
                    END IF;
                END $$;
                """,
                viewName, viewName,viewName, state);
    }

    private String refreshMaterializedViewSQL(String viewName) {
        return String.format("REFRESH MATERIALIZED VIEW CONCURRENTLY %s;", viewName);
    }

    @Override
    public void createMaterializedView() {
        materializedViewsRepository.addMaterializedView();
    }

    @Override
    public void refreshMaterializedView() {

    }
}
