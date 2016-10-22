package ru.vaddya.schedule.core.utils;

/**
 * Created by Vadim on 9/25/2016.
 */
public enum LessonType {
    LECTURE("Лекции"),
    PRACTICE("Практика"),
    SEMINAR("Семинар"),
    LAB("Лабораторные"),
    CONSULTATION("Консультация"),
    EXAM("Экзамен"),
    TEST("Контрольная работа");

    private String ru;
    private String en;

    LessonType(String ru) {
        this.ru = ru;
        this.en = name().substring(1).toLowerCase();
    }

    public String en() {
        return en;
    }

    public String ru() {
        return ru;
    }
}