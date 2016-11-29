package ru.vaddya.schedule.core.db.orchestrate;

import io.orchestrate.client.*;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.lessons.ChangedLesson;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.LessonType;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static ru.vaddya.schedule.core.utils.Dates.SHORT_DATE_FORMAT;

/**
 * Реализация взаимодействия с OrchestrateDB
 *
 * @author vaddya
 */
public class OrchestrateDB implements Database {

    private static final Database db = new OrchestrateDB();
    private static final Client client = new OrchestrateClient("b8ed25ed-1c39-41be-bdab-ec717e105049");
    private static final Logger logger = Logger.getLogger("OrchestrateDB");

    private static final String TASKS = "tasks";
    private static final String LESSONS = "lessons";

    private static final String ODD = "odd";
    private static final String EVEN = "even";
    private static final String CHANGES = "changes";

    private static final int LIMIT = 100;

    private OrchestrateDB() {
    }

    public static Database getConnection() {
        return db;
    }


    @Override
    public List<Lesson> getLessons(WeekType week, DayOfWeek day) {
        List<Lesson> list = new ArrayList<>();
        KvList<LessonPOJO> response = client
                .listCollection(LESSONS)
                .limit(LIMIT)
                .get(LessonPOJO.class)
                .get();
        for (KvObject<LessonPOJO> obj : response) {
            LessonPOJO pojo = obj.getValue();
            if (pojo.getDay().equals(day.toString())) {
                logger.fine("Lesson was read: " + pojo);
                Lesson lesson = new Lesson.Builder()
                        .id(obj.getKey())
                        .startTime(pojo.getStartTime())
                        .endTime(pojo.getEndTime())
                        .subject(pojo.getSubject())
                        .type(pojo.getType())
                        .place(pojo.getPlace())
                        .teacher(pojo.getTeacher())
                        .build();
                list.add(lesson);
            }
        }
        return list;
    }

    @Override
    public boolean addLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        KvMetadata metadata = client
                .kv(LESSONS, lesson.getId().toString())
                .put(LessonPOJO.of(day, lesson))
                .get();
        logger.fine("Lesson was added: " + metadata.toString());
        return true;
    }

    @Override
    public boolean updateLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        return addLesson(week, day, lesson);
    }

    @Override
    public boolean changeLessonDay(WeekType week, DayOfWeek from, DayOfWeek to, Lesson lesson) {
        removeLesson(week, from, lesson);
        addLesson(week, to, lesson);
        return false;
    }

    @Override
    public boolean removeLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        boolean res = client
                .kv(LESSONS, lesson.getId().toString())
                .delete()
                .get();
        if (res) {
            logger.fine("Lesson " + lesson.getSubject() + " was removed");
        } else {
            logger.warning("Lesson " + lesson.getSubject() + " wasn't removed");
        }
        return res;
    }


    @Override
    public List<ChangedLesson> getChanges(LocalDate date) {
        return new ArrayList<>();
    }

    @Override
    public boolean addLesson(ChangedLesson lesson) {
        return false;
    }

    @Override
    public boolean updateLesson(ChangedLesson lesson) {
        return false;
    }

    @Override
    public boolean changeLessonDay(LocalDate from, LocalDate to, Lesson lesson) {
        return false;
    }

    @Override
    public boolean removeLesson(ChangedLesson lesson) {
        return false;
    }


    @Override
    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        KvList<TaskPOJO> response = client
                .listCollection(TASKS)
                .limit(LIMIT)
                .get(TaskPOJO.class)
                .get();
        for (KvObject<TaskPOJO> obj : response) {
            TaskPOJO pojo = obj.getValue();
            logger.fine("Task was read: " + pojo);
            Task task = new Task.Builder()
                    .id(UUID.fromString(obj.getKey()))
                    .subject(pojo.getSubject())
                    .type(LessonType.valueOf(pojo.getType()))
                    .deadline(SHORT_DATE_FORMAT.parse(pojo.getDeadline()))
                    .textTask(pojo.getTextTask())
                    .isComplete(pojo.isComplete())
                    .build();
            list.add(task);
        }
        return list;
    }

    @Override
    public boolean addTask(Task task) {
        KvMetadata metadata = client
                .kv(TASKS, task.getId().toString())
                .put(TaskPOJO.of(task))
                .get();
        logger.fine("Task was added: " + metadata.toString());
        return true;
    }

    @Override
    public boolean updateTask(Task task) {
        return addTask(task);
    }

    @Override
    public boolean removeTask(Task task) {
        boolean res = client
                .kv(TASKS, task.getId().toString())
                .delete()
                .get();
        if (res) {
            logger.fine("Task " + task.getTextTask() + " was removed");
        } else {
            logger.warning("Task " + task.getTextTask() + " wasn't removed");
        }
        return res;
    }
}