package kaloyan.state_population_backend.utils;

import jakarta.persistence.Converter;

public enum UsState {
}

@Converter(autoApply = true)
public class MyEnumConverter implements AttributeConverter<UsState, String> {

    @Override
    public String convertToDatabaseColumn(MyEnum attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public MyEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? MyEnum.fromCode(dbData) : null;
    }
}