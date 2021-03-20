package ru.systemairac.calculator.domain.humidifier;

public enum TypeCylinder {
    DISMOUNTABLE_CYLINDER("Разборный цилиндр"), MOUNTABLE_CYLINDER("Неразборный цилиндр");
    private final String txt;

    TypeCylinder(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static TypeCylinder getTypeByTxt(String txt) {
        for (TypeCylinder env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
