package ru.systemairac.calculator.myenum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConverterHumidifierComponentType implements AttributeConverter<HumidifierComponentType, String> {

    @Override
    public String convertToDatabaseColumn(HumidifierComponentType attribute) {
        return attribute.getTxt();
    }

    @Override
    public HumidifierComponentType convertToEntityAttribute(String value) {
        return value == null ? null : HumidifierComponentType.getTypeByTxt(value);
    }
}