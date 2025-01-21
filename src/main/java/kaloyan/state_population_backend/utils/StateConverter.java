package kaloyan.state_population_backend.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StateConverter implements AttributeConverter<State, String> {

    @Override
    public String convertToDatabaseColumn(State state) {
        return state != null ? state.getStateName() : null;
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        return dbData != null ? State.fromStateName(dbData) : null;
    }

}
