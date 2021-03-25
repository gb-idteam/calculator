package ru.systemairac.calculator.myenum;

public enum EnumHumidifierType {
    HEATING_ELEMENT("Тэновый"), ELECTRODE("Электродный");
    private final String txt;

    EnumHumidifierType(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static EnumHumidifierType getTypeByTxt(String txt) {
        for (EnumHumidifierType env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
