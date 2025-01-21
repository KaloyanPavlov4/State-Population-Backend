package kaloyan.state_population_backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "state_population_mv") // Matches the materialized view name
public class State {

    @Id
    private String state; // State name as the primary key

    private long totalPopulation; // Sum of county populations

    // Getters and setters
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }
}
