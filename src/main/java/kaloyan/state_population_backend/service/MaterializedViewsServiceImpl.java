package kaloyan.state_population_backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class MaterializedViewsServiceImpl implements MaterializedViewsService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String CREATE_QUERY = """
            DO $$
            BEGIN
                IF NOT EXISTS (
                   SELECT 1
                   FROM pg_matviews
                   WHERE matviewname = '%s'
                ) THEN
                    EXECUTE $view$
                        CREATE MATERIALIZED VIEW %s AS
                        %s
                    $view$;
                  ELSE
                    EXECUTE $view$ REFRESH MATERIALIZED VIEW %s $view$;
                END IF;
            END $$;""";

    private static final String REFRESH_QUERY = "REFRESH MATERIALIZED VIEW %s";

    @Override
    @Modifying
    @Transactional
    public int createMaterializedView(String viewName, String query) {
        return entityManager.createNativeQuery(String.format(CREATE_QUERY,viewName,viewName,query,viewName)).executeUpdate();
    }

    @Override
    @Modifying
    @Transactional
    public int refreshMaterializedView(String viewName) {
        return entityManager.createNativeQuery(String.format(REFRESH_QUERY, viewName)).executeUpdate();
    }
}
