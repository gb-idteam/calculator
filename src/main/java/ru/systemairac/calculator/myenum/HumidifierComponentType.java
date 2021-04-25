package ru.systemairac.calculator.myenum;

public enum HumidifierComponentType  {
    CYLINDER_CASING("Кожух цилиндра"),
    CYLINDER_WATER_HEATING_KIT("Система подогрева воды в цилиндре [+65°C]"),
    CYLINDER("Разборн. Цилиндр"),
    DRAINAGE_COOLING_KIT("Комплект охлаждения дренажа"),
    FAN_DISTRIBUTOR("Вентиляторный распределитель"),
    LEAK_SENSOR("Датчик протечки"),
    LED_DISPLAY("ЖК дисплей"),
    MASTER_SLAVE_PANEL("Панель Master/Slave"),
    CABLE_FOR_LED_DISPLAY("Кабель для ЖК дисплея [10м max]"),
    OTHER_DIAMETER_ADAPTER("Переходник Ø40/25 мм"),
    PROTECTIVE_CABINET("Шкаф IP55"),
    ADAPTER_60_25("Переходник Ø60/25 мм"),
    ADAPTER_60_40("Переходник Ø60/40 мм"),
    ADAPTER_60_55("Переходник Ø60/55 мм"),
    FRAME_FOR_CABINET("Рама для напольного монтажа"),
    DUCT_SENSOR("Канальный датчик"),
    ROOM_HUMIDITY_LED_SENSOR("Комнатный датчик влажности (с дисплеем)"),
    ROOM_HUMIDITY_SENSOR("Комнатный датчик влажности (настенный)"),
    DUCT_LIMIT_HYGROSTAT("Канальный ограничительный гигростат"),
    WATER_TUBES("Трубки для подачи(0,5 м ,с фитингом 3/4”)  и слива воды(1м,Ø=60мм)"),
    VAPOR_INLET_TUBE_25("Трубка для подачи пара армированная синтетической нитью d=25 мм (цена за метр)"),
    VAPOR_INLET_TUBE_40("Трубка для подачи пара армированная синтетической нитью d=40 мм (цена за метр)"),
    VAPOR_INLET_TUBE_60("Трубка для подачи пара армированная синтетической нитью d=60 мм (цена за метр)"),
    CONDENSATE_OUTLET_TUBE_8("Трубка для отвода конденсата для канальных парораспределителей, d = 8мм (Цена за метр)"),
    CONDENSATE_OUTLET_TUBE_13("Трубка для отвода конденсата для канальных парораспределителей, d = 13мм (Цена за метр)"),
    Y_ADAPTER("Y переходник");
    private final String txt;

    HumidifierComponentType(String txt) {
        this.txt= txt;
    }

    public String getTxt() {
        return txt;
    }

    public static HumidifierComponentType getTypeByTxt(String txt) {
        for (HumidifierComponentType env : values()) {
            if (env.getTxt().equals(txt)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with url: [" + txt + "]");
    }
}
