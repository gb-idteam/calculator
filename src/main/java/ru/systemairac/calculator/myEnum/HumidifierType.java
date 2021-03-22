package ru.systemairac.calculator.myEnum;

public enum HumidifierType {
    HEATING_ELEMENT("Тэновый"), ELECTRODE("Электродный");
    private final String txt;

    HumidifierType(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static HumidifierType getTypeByTxt(String txt) {
        for (HumidifierType env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
