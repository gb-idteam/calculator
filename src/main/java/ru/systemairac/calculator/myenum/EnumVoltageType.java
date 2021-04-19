package ru.systemairac.calculator.myenum;

public enum EnumVoltageType {
    THREE_PHASE_380V("3~380"),ONE_PHASE_220V("1~220");
    private final String txt;

    EnumVoltageType(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static EnumVoltageType getTypeByTxt(String txt) {
        for (EnumVoltageType env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
