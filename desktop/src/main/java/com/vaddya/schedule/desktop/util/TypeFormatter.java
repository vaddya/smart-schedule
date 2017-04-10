package com.vaddya.schedule.desktop.util;

import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.desktop.Main;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для перевода перечислений
 *
 * @author vaddya
 */
public class TypeFormatter {

    public static String format(TypeOfWeek week) {
        return Main.getString("type_of_week_" + week.toString().toLowerCase());
    }

    public static String format(LessonType lesson) {
        return Main.getString("lesson_type_" + lesson.toString().toLowerCase());
    }

    public static String format(DayOfWeek day) {
        return Main.getString("day_of_week_" + day.toString().toLowerCase());
    }

    public static List<String> formatTypesOfWeek() {
        return Arrays.stream(TypeOfWeek.values())
                .map(TypeFormatter::format)
                .collect(Collectors.toList());
    }

    public static List<String> formatLessonTypes() {
        return Arrays.stream(LessonType.values())
                .map(TypeFormatter::format)
                .collect(Collectors.toList());
    }

    public static List<String> formatDaysOfWeek() {
        return Arrays.stream(DayOfWeek.values())
                .map(TypeFormatter::format)
                .collect(Collectors.toList());
    }

    public static TypeOfWeek parseTypeOfWeek(String string) {
        for (TypeOfWeek week : TypeOfWeek.values()) {
            if (format(week).equals(string)) return week;
        }
        return TypeOfWeek.BOTH;
    }

    public static LessonType parseLessonType(String string) {
        for (LessonType lesson : LessonType.values()) {
            if (format(lesson).equals(string)) return lesson;
        }
        return LessonType.ANOTHER;
    }

    public static DayOfWeek parseDayOfWeek(String string) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (format(day).equals(string)) return day;
        }
        return DayOfWeek.MONDAY;
    }

}