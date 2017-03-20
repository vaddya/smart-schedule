package com.vaddya.schedule.core.db;

import com.vaddya.schedule.core.lessons.ChangedLesson;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс взаимодействия с базой данных
 *
 * @author vaddya
 */

// TODO: Уже хочется вынести работу с бд в отдельный модуль из ядра.
// Еще не критично, но уже можно было бы
// Интерфейс остается здесь, и хочется что-то типа фабрики для баз данных

public interface Database {
//  TODO: чтобы не писать тут явно орчестрейт, а вызвать метод "дайКоннекшн()",
// интерфейс для этого дайКоннекшн в модуле ядра, а реализация в модуле БД,
// и там и делается выбор нужной бд
    static Database getConnection() {
        return FakeDB.getConnection();
    }


    Map<DayOfWeek, List<Lesson>> getLessons(WeekType week);

    boolean addLesson(WeekType week, DayOfWeek day, Lesson lesson);

    boolean updateLesson(WeekType week, DayOfWeek day, Lesson lesson);

    boolean changeLessonDay(WeekType week, DayOfWeek from, DayOfWeek to, Lesson lesson);

    boolean removeLesson(WeekType week, DayOfWeek day, Lesson lesson);


    List<ChangedLesson> getChanges(LocalDate date);

    boolean addChange(ChangedLesson lesson);

    boolean removeChange(ChangedLesson lesson);

    boolean removeAllChanges();


    List<Task> getTasks();

    boolean addTask(Task task);

    boolean updateTask(Task task);

    boolean removeTask(Task task);
}
