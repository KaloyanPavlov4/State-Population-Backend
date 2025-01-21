package kaloyan.state_population_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT * FROM state_population_mv")
@Data
public class StatePopulation {

    @Id
    private String state;

    private long totalPopulation;
}
