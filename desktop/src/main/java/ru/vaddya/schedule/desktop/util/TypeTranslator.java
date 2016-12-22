package ru.vaddya.schedule.desktop.util;

import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.desktop.Main;

import java.util.Locale;

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

}
