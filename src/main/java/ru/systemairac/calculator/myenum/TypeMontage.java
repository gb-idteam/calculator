package ru.systemairac.calculator.myenum;

public enum TypeMontage {
    DUCT("В канале"), AHU("В центральном кондиционере"), FAN_DISTRIBUTOR("Вентиляторный распределитель");
    private final String txt;

    TypeMontage(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static TypeMontage getTypeByTxt(String txt) {
        for (TypeMontage env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
