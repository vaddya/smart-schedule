package ru.vaddya.schedule.core.lessons;

import ru.vaddya.schedule.core.utils.LessonChanges;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Класс для представления измененного урока (перенесенного или отмененного)
 *
 * @author vaddya
 */
public class ChangedLesson {

    private final LessonChanges changes;

    private LocalDate date;

    private final Lesson lesson;

    public ChangedLesson(LessonChanges changes, LocalDate date, Lesson lesson) {
        this.changes = changes;
        this.date = date;
        this.lesson = lesson;
    }

    public LessonChanges getChanges() {
        return changes;
    }

    public LocalDate getDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public Lesson getLesson() {
        return lesson;
    }
}
