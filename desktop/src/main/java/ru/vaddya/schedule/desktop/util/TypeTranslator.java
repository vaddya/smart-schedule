package ru.vaddya.schedule.desktop.util;

import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.desktop.Main;

import java.time.DayOfWeek;
import java.util.Locale;

import static java.time.DayOfWeek.*;
import static ru.vaddya.schedule.core.lessons.LessonType.*;

/**
 * ru.vaddya.schedule.desktop.util at smart-schedule
 *
 * @author vaddya
 * @since December 22, 2016
 */
public class TypeTranslator {

    public static LessonType parseLessonType(String type) {
        if (Main.bundle.getLocale().equals(new Locale("ru"))) {
            switch (type) {
                case "Лекции":
                    return LECTURE;
                case "Упраженния":
                    return PRACTICE;
                case "Семинар":
                    return SEMINAR;
                case "Лабораторные":
                    return LAB;
                case "Контрольная работа":
                    return TEST;
                case "Консультации":
                    return CONSULTATION;
                case "Экзамен":
                    return EXAM;
                case "Другое":
                    return ANOTHER;
            }
        }
        return LessonType.valueOf(type.toUpperCase());
    }

    public static DayOfWeek parseDayOfWeek(String day) {
        if (Main.bundle.getLocale().equals(new Locale("ru"))) {
            switch (day) {
                case "Понедельник":
                    return MONDAY;
                case "Вторник":
                    return TUESDAY;
                case "Среда":
                    return WEDNESDAY;
                case "Четверг":
                    return THURSDAY;
                case "Пятница":
                    return FRIDAY;
                case "Суббота":
                    return SATURDAY;
                case "Воскресенье":
                    return SUNDAY;
            }
        }
        return DayOfWeek.valueOf(day.toUpperCase());
    }
}
