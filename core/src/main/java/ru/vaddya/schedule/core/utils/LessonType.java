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

    LessonType(String ru) {
        this.ru = ru;
    }

    public String getRu() {
        return ru;
    }
}