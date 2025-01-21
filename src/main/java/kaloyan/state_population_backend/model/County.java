package kaloyan.state_population_backend.model;

import jakarta.persistence.*;
import kaloyan.state_population_backend.utils.State;
import kaloyan.state_population_backend.utils.StateConverter;
import lombok.Data;

@Entity
@Table(name = "counties", uniqueConstraints = @UniqueConstraint(columnNames = {"state", "name"}))
@Data
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = StateConverter.class)
    private State state;

    private long population;
}
