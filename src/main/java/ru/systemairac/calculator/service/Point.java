package ru.systemairac.calculator.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Point {

    private final static double ATMOSPHERE_PRESSURE = 94.5; // kPa
    private static final double HEAT_CAPACITY_AIR_DRY = 1.006; // kJ / (kg * K)
    private static final double HEAT_OF_FUSION_WATER_0 = 2501.000; // kJ / kg
    private static final double HEAT_OF_FUSION_WATER_DELTA = 2.360; // kJ / (kg * K)
    private static final double HEAT_CAPACITY_WATER_VAPOR = 1.860; // kJ / (kg * K)
    private static final double MOLAR_RATIO = 0.622;
    private Double pressureD; // kPa
    private Double pressureS; // kPa парциальное давление
    private Double temperature; //°C
    private Double humidity; //%
    private Double enthalpy; //kJ/kg
    private Double moistureContent; // g/kg
    private Double density; // kg/m³

    private Point(PointBuilder builder) {
        this.temperature = calcTemperature(builder);
        this.pressureD = calcPressureD(temperature);
        this.humidity = calcHumidity(builder);
        this.pressureS = calcPressureS(humidity);
        this.enthalpy = calcEnthalpy(builder);
        this.moistureContent = calcMoistureContent(builder);
        this.density = calcDensity(builder);
    }

    private double calcPressureS(Double u) {
        return (u / 100) * pressureD / 1000;
    }

    private Double calcDensity(PointBuilder builder) {
        Double t = calcTemperature(builder);
        Double d = calcHumidity(builder);
//        ATMOSPHERE_PRESSURE * 1000 * (h / 1000 + 1) / 461.39 / (273.15 + t) / (h / 1000 + 0.6222)
        return (ATMOSPHERE_PRESSURE * 10) / (4.62 * (0.622 + d / 1000) * (t + 273.5));
    }

    private Double calcMoistureContent(PointBuilder builder) {
        if (builder.moistureContent != null) {
            return builder.moistureContent;
        }
        if (builder.temperature!= null && builder.enthalpy!=null) {
            double moistureContentByT = 622 * calcPressureS(100.0) / (ATMOSPHERE_PRESSURE - calcPressureS(100.0));
            double moistureContentByH = (enthalpy - 1.006 * temperature) / (HEAT_OF_FUSION_WATER_0 + 1.805 * temperature) * 1000;
            return Math.min(moistureContentByT, moistureContentByH);
        }
        if ((builder.humidity!= null && builder.enthalpy!=null) || (builder.temperature!= null && builder.humidity!=null)) {
            return 622 * pressureS / (ATMOSPHERE_PRESSURE - pressureS);
        }
        return null;
    }

    private Double calcEnthalpy(PointBuilder builder) {
        if (builder.enthalpy != null) {
            return builder.enthalpy;
        }
        this.temperature  = builder.temperature != null ? builder.temperature : calcTemperature(builder);
        this.moistureContent = builder.moistureContent != null ? builder.moistureContent : calcMoistureContent(builder);
        return 1.006 * temperature + (HEAT_OF_FUSION_WATER_0 + 1.805 * temperature) * moistureContent / 1000;
    }

    private Double calcHumidity(PointBuilder builder) {
        if (builder.humidity != null) {
            return builder.humidity;
        }
        if (builder.temperature!= null && builder.moistureContent!=null) {
            return ATMOSPHERE_PRESSURE / pressureD *1000 / (0.622 / moistureContent * 1000 + 1) * 100;
        }
        return null;
    }

    private Double calcTemperature(PointBuilder builder) {
        if (builder.temperature != null) {
            return builder.temperature;
        }
        if (builder.humidity!= null && builder.moistureContent!=null) {
            if (builder.humidity.equals(0))
                return null;
            this.pressureD = builder.moistureContent/1000 * ATMOSPHERE_PRESSURE/(builder.humidity/100)/(0.622 + builder.moistureContent/1000)*1000;
            if (pressureD<641)
                return (271 * Math.log(pressureD)-1738.4) / (28.74 - Math.log(pressureD));
            else
                return (234 * Math.log(pressureD)-1500.3) / (23.5- Math.log(pressureD));
        }
        if (builder.humidity!= null && builder.enthalpy!=null) {
            return iterTemp(builder.humidity, builder.enthalpy);
        }
        if (builder.enthalpy!= null && builder.moistureContent!=null) {
            Double t1 = (enthalpy - HEAT_OF_FUSION_WATER_0 * moistureContent /1000)/(1.006 + 1.805 * moistureContent/1000);
            Double t2 = iterTemp(100.0, builder().enthalpy);
            return Math.max(t1,t2);
        }
        this.pressureD = calcPressureD(this.temperature);
        return null;
    }

        public Double iterTemp(Double humidity, Double enthalpy) {
            double tHigh = 100, tLow = -50;
            final double EPSILON = 0.0001;
            Double tMid = (tHigh + tLow)/2;
            while (Math.abs(tHigh - tLow) > EPSILON) {
                tMid = (tHigh + tLow)/2;
                double eMid = calcEnthalpyFromTemperatureAndHumidity(tMid, humidity);
                if (eMid == enthalpy) {
                    return tMid;
                } else if (eMid < enthalpy) {
                    tLow = tMid;
                } else {
                    tHigh = tMid;
                }
            }
            return tMid;
        }

    private double calcEnthalpyFromTemperatureAndHumidity(double temperature, double humidity) {
        double pressureSaturatedVapor = calcPressureD(temperature);
        return HEAT_CAPACITY_AIR_DRY * temperature + (HEAT_OF_FUSION_WATER_0 - HEAT_OF_FUSION_WATER_DELTA * temperature
                + HEAT_CAPACITY_WATER_VAPOR * temperature) * MOLAR_RATIO * pressureSaturatedVapor * humidity/100
                / (ATMOSPHERE_PRESSURE * 1000 - pressureSaturatedVapor);
    }

    private Double calcPressureD(Double t) {
        if (t<=-50 || t>100) {
            throw new IllegalArgumentException("Температура должна лежать в диапазоне от -50 до 100. Задаянная температура: " + t );
        }
        if (t<0)
            return Math.exp((1738.4+28.74*t)/(271+t));
        else
            return Math.exp((1500.3+23.5*t)/(234+t));
    }

    public static class PointBuilder {
        private Double temperature;
        private Double humidity;
        private Double enthalpy;
        private Double moistureContent;

        private static final int MAX_PARAMETERS_DEFINED = 2;

        private int definedParametersCounter = 0;

        private void checkAndIncrementCounter() {
            if (definedParametersCounter >= MAX_PARAMETERS_DEFINED)
                throw new RuntimeException("Задано больше двух определяющих параметров.");
            definedParametersCounter++;
        }

        public PointBuilder temperature(Double temperature) {
            checkAndIncrementCounter();
            this.temperature = temperature;
            return this;
        }

        public PointBuilder humidity(Double humidity) {
            checkAndIncrementCounter();
            this.humidity = humidity;
            return this;
        }

        public PointBuilder enthalpy(Double enthalpy) {
            checkAndIncrementCounter();
            this.enthalpy = enthalpy;
            return this;
        }

        public PointBuilder moistureContent(Double moistureContent) {
            checkAndIncrementCounter();
            this.moistureContent = moistureContent;
            return this;
        }

        public Point build() {
            return new Point(this);
        }
    }

    public static PointBuilder builder() {
        return new PointBuilder();
    }
}
