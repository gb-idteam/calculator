package ru.systemairac.calculator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {

    private final Double ATMOSPHERE_PRESSURE = 94.5;

    private Double temperature;
    private Integer humidity;
    private Double enthalpy;
    private Double moistureContent;
    private Double density;

    private PointDto(PointBuilder builder) {
        this.temperature = calcTemperature(builder);
        this.humidity = calcHumidity(builder);
        this.enthalpy = calcEnthalpy(builder);
        this.moistureContent = calcMoistureContent(builder);
        this.density = calcDensity(builder);
    }

    private Double calcDensity(PointBuilder builder) {
        Double t = calcTemperature(builder);
        Integer h = calcHumidity(builder);

        return ATMOSPHERE_PRESSURE * 1000 * (h / 1000 + 1) / 461.39 / (273.15 + t) / (h / 1000 + 0.6222);
    }

    private Double calcMoistureContent(PointBuilder builder) {
        if (builder.moistureContent != null) {
            return builder.moistureContent;
        }

        //TODO дописать формулу для расчета влагосодержания
        return null;
    }

    private Double calcEnthalpy(PointBuilder builder) {
        if (builder.enthalpy != null) {
            return builder.enthalpy;
        }
        Double t = builder.temperature != null ? builder.temperature : calcTemperature(builder);
        Double mc = builder.moistureContent != null ? builder.moistureContent : calcMoistureContent(builder);

        return 1.01 * t + (2501 + 1.86 * t) * mc / 1000;
    }

    private Integer calcHumidity(PointBuilder builder) {
        if (builder.humidity != null) {
            return builder.humidity;
        }

        //TODO дописать формулу для расчета влажности
        return null;
    }


    private Double calcTemperature(PointBuilder builder) {
        if (builder.temperature != null) {
            return builder.temperature;
        }

        //TODO дописать формулу для расчета температуры
        return null;
    }

    public static class PointBuilder {
        private Double temperature;
        private Integer humidity;
        private Double enthalpy;
        private Double moistureContent;

        public PointBuilder setTemperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public PointBuilder setHumidity(Integer humidity) {
            this.humidity = humidity;
            return this;
        }

        public PointBuilder setEnthalpy(Double enthalpy) {
            this.enthalpy = enthalpy;
            return this;
        }

        public PointBuilder setMoistureContent(Double moistureContent) {
            this.moistureContent = moistureContent;
            return this;
        }

        public PointDto build() {
            return new PointDto(this);
        }
    }
}
