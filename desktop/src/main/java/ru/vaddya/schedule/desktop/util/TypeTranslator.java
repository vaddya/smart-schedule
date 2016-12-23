package ru.vaddya.schedule.desktop.util;

import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.desktop.Main;

import java.time.DayOfWeek;

/**
 * Вспомогательный класс для перевода перечислений
 *
 * @author vaddya
 */
public class TypeTranslator {

    public static LessonType parseLessonType(String string) {
        for (LessonType type : LessonType.values()) {
            if (Main.getBundle().getString(type.toString().toLowerCase()).equals(string)) return type;
        }
        return LessonType.ANOTHER;
    }

    public static DayOfWeek parseDayOfWeek(String string) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (Main.getBundle().getString(day.toString().toLowerCase()).equals(string)) return day;
        }
        return DayOfWeek.MONDAY;
    }
}
