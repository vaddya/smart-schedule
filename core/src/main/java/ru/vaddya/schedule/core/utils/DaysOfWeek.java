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

    DaysOfWeek(String ru) {
        this.ru = ru;
    }

    public String getRu() {
        return ru;
    }

    public static DaysOfWeek valueOfRu(String ru) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (ru.equals(day.getRu())) {
                return day;
            }
        }
        return null;
    }
}
