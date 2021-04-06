package ru.systemairac.calculator.repository.humidifier;

import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;

public class HumidifierFilter {

    private Double minimalCapacity;
    private EnumVoltageType voltage;
    private EnumHumidifierType type;

    public HumidifierFilter(Double minimalCapacity, EnumVoltageType voltage, EnumHumidifierType type) {
        this.minimalCapacity = minimalCapacity;
        this.voltage = voltage;
        this.type = type;
    }

    public Double getMinimalCapacity() {
        return minimalCapacity;
    }

    public EnumVoltageType getVoltage() {
        return voltage;
    }

    public EnumHumidifierType getType() {
        return type;
    }
}
