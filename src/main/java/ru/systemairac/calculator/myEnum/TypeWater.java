package ru.systemairac.calculator.myEnum;

public enum TypeWater {
    TAP_WATER("Водопроводная вода"), DEMINERALIZED_WATER("Деминерализованная вода");
    private final String txt;

    TypeWater(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static TypeWater getTypeByTxt(String txt) {
        for (TypeWater env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
