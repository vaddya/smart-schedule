package ru.vaddya.schedule.core.utils;

/**
 * Created by Vadim on 10/5/2016.
 */
public enum DaysOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскрсенье");

    private String ru;
    private String en;

    DaysOfWeek(String ru) {
        this.ru = ru;
        this.en = name().substring(1).toLowerCase();
    }

    public String ru() {
        return ru;
    }

    public String en() { return en; }

    public static DaysOfWeek valueOfRu(String ru) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (ru.equals(day.ru())) {
                return day;
            }
        }
        return null;
    }
}
