package ru.vaddya.schedule.core.utils;

/**
 * Перечисление типов занятий
 *
 * @author vaddya
 */
public enum LessonType {
    // TODO: 10/23/2016 Интернационализация из файла
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
        this.en = name().charAt(0) + name().substring(1).toLowerCase();
    }

    public String en() {
        return en;
    }

    public String ru() {
        return ru;
    }
}