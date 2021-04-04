package ru.systemairac.calculator.repository.humidifier;

import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;

public class HumidifierFilter {

    private Double minimalCapacity;
    private EnumVoltageType voltageType;
    private EnumHumidifierType type;

    public HumidifierFilter(Double minimalCapacity, EnumVoltageType voltageType, EnumHumidifierType type) {
        this.minimalCapacity = minimalCapacity;
        this.voltageType = voltageType;
        this.type = type;
    }

    public Double getMinimalCapacity() {
        return minimalCapacity;
    }

    public EnumVoltageType getVoltageType() {
        return voltageType;
    }

    public EnumHumidifierType getType() {
        return type;
    }
}
