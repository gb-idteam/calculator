package ru.systemairac.calculator.repository.humidifier;

import ru.systemairac.calculator.myenum.EnumHumidifierType;

public class HumidifierFilter {

    private Double minimalCapacity;
    private Integer phasicity;
    private EnumHumidifierType type;

    public HumidifierFilter(Double minimalCapacity, Integer phasicity, EnumHumidifierType type) {
        this.minimalCapacity = minimalCapacity;
        this.phasicity = phasicity;
        this.type = type;
    }

    public Double getMinimalCapacity() {
        return minimalCapacity;
    }

    public Integer getPhasicity() {
        return phasicity;
    }

    public EnumHumidifierType getType() {
        return type;
    }
}
