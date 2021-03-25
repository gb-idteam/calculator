package ru.systemairac.calculator.myenum;

public enum TableName {
    HUMIDIFIER("tbl_humidifiers"), DEHUMIDIFIER("tbl_dehumidifiers");
    private final String txt;

    TableName(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static TableName getTypeByTxt(String txt) {
        for (TableName env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
