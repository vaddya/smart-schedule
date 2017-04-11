package com.vaddya.schedule.desktop.util;

import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.desktop.Main;
import javafx.util.StringConverter;

import java.time.DayOfWeek;

/**
 * Вспомогательный класс для перевода перечислений
 *
 * @author vaddya
 */
public class TypeConverters {

    private static final StringConverter<TypeOfWeek> typeOfWeekConverter = new StringConverter<TypeOfWeek>() {
        @Override
        public String toString(TypeOfWeek object) {
            return Main.getString("type_of_week_" + object.toString().toLowerCase());
        }

        @Override
        public TypeOfWeek fromString(String string) {
            for (TypeOfWeek week : TypeOfWeek.values()) {
                if (toString(week).equals(string)) return week;
            }
            return TypeOfWeek.BOTH;
        }
    };

    private static final StringConverter<LessonType> lessonTypeConverter = new StringConverter<LessonType>() {
        @Override
        public String toString(LessonType object) {
            return Main.getString("lesson_type_" + object.toString().toLowerCase());
        }

        @Override
        public LessonType fromString(String string) {
            for (LessonType lesson : LessonType.values()) {
                if (toString(lesson).equals(string)) return lesson;
            }
            return LessonType.ANOTHER;
        }
    };

    private static final StringConverter<DayOfWeek> dayOfWeekConverter = new StringConverter<DayOfWeek>() {
        @Override
        public String toString(DayOfWeek object) {
            return Main.getString("day_of_week_" + object.toString().toLowerCase());

        }

        @Override
        public DayOfWeek fromString(String string) {
            for (DayOfWeek day : DayOfWeek.values()) {
                if (toString(day).equals(string)) return day;
            }
            return DayOfWeek.MONDAY;
        }
    };

    public static StringConverter<TypeOfWeek> getTypeOfWeekConverter() {
        return typeOfWeekConverter;
    }

    public static StringConverter<LessonType> getLessonTypeConverter() {
        return lessonTypeConverter;
    }

    public static StringConverter<DayOfWeek> getDayOfWeekConverter() {
        return dayOfWeekConverter;
    }

}