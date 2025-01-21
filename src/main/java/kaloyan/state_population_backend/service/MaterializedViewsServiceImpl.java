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

    private final String createQuery = """
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
                END IF;
            END $$;""";

    private final String refreshQuery = "REFRESH MATERIALIZED VIEW CONCURRENTLY %s";

    @Override
    @Modifying
    @Transactional
    public int createMaterializedView(String viewName, String query) {
        return entityManager.createNativeQuery(String.format(createQuery,viewName,viewName,query)).executeUpdate();
    }

    @Override
    @Modifying
    @Transactional
    public int refreshMaterializedView(String viewName) {
        return entityManager.createNativeQuery(String.format(refreshQuery, viewName)).executeUpdate();
    }
}
