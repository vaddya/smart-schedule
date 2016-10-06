package ru.vaddya.schedule.core;

/**
 * Created by Vadim on 9/25/2016.
 */
public enum LessonType {
    LECTURE("Лекция"),
    PRACTICE("Практика"),
    SEMINAR("Семинар"),
    LAB("Лабораторная"),
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

    public static LessonType valueOfRu(String ru) {
        for (LessonType type : LessonType.values()) {
            if (ru.equals(type.getRu())) {
                return type;
            }
        }
        return null;
    }
}