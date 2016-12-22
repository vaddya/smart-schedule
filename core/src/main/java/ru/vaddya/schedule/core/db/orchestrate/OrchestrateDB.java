package ru.vaddya.schedule.core.db.orchestrate;

import io.orchestrate.client.*;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.lessons.ChangedLesson;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.LessonChanges;
import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.core.utils.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static ru.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;

/**
 * Реализация взаимодействия с OrchestrateDB
 *
 * @author vaddya
 */
public class OrchestrateDB implements Database {

    private static final Database db = new OrchestrateDB();
    //TODO: вот этот api key сейчас нельзя поменять.
    //В качестве первого шага нужно его хранить в файле, а сюда передавать строкой.
    //Файлов может быть много, их читает то приложение, которое запускает работу с бд,
    //это могут быть интеграционные тесты -- файлик тестовых ресурсах, причем файлик есть, а ключ в него я, например,
    // вписываю сама, когда вы мне его на ухо шепнете при встрече, или скажете мне, завести свой орчестрейт,
    // и я в файл свой ключ вставляю. В репозитории не храним, обычно.
    // ваше консольное или десктоп-приложение -- у них свои файлы по тому же принципу, или там через гуи задается
    // главное -- что не в код вшито
    private static final Client client = new OrchestrateClient("c8840a76-13ef-4770-b3c7-59c0ae659ac6");
    private static final Logger logger = Logger.getLogger("OrchestrateDB");

    private static final String TASKS = "TASKS";
    private static final String CHANGES = "CHANGES";

    private static final int LIMIT = 100;

    private OrchestrateDB() {
    }

    public static Database getConnection() {
        return db;
    }

    @Override
    public Map<DayOfWeek, List<Lesson>> getLessons(WeekType week) {
        Map<DayOfWeek, List<Lesson>> lists = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            lists.put(day, new ArrayList<>());
        }
        KvList<LessonPOJO> response = client
                .listCollection(week.toString())
                .limit(LIMIT)
                .get(LessonPOJO.class)
                .get(30, TimeUnit.SECONDS);
        for (KvObject<LessonPOJO> obj : response) {
            LessonPOJO pojo = obj.getValue();
            Lesson lesson = LessonPOJO.parse(obj.getKey(), pojo);
            logger.fine("Lesson was read: " + pojo);
            lists.get(DayOfWeek.valueOf(pojo.getDay())).add(lesson);
        }
        return lists;
    }

    public List<Lesson> getLessons(WeekType week, DayOfWeek day) {
        List<Lesson> list = new ArrayList<>();
        SearchResults<LessonPOJO> results = client
                .searchCollection(week.toString())
                .limit(LIMIT)
                .get(LessonPOJO.class, "day:" + day)
                .get(30, TimeUnit.SECONDS);
        for (Result<LessonPOJO> result : results) {
            LessonPOJO pojo = result.getKvObject().getValue();
            Lesson lesson = LessonPOJO.parse(result.getKvObject().getKey(), pojo);
            logger.fine("Lesson was read: " + pojo);
            list.add(lesson);
        }
        return list;
    }

    @Override
    public boolean addLesson(WeekType week, DayOfWeek day, Lesson lesson) {
        KvMetadata metadata = client
                .kv(week.toString(), lesson.getId().toString())
                .put(LessonPOJO.of(day, lesson))
                .get(30, TimeUnit.SECONDS);
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
                .kv(week.toString(), lesson.getId().toString())
                .delete()
                .get(30, TimeUnit.SECONDS);
        if (res) {
            logger.fine("Lesson " + lesson.getSubject() + " was removed");
        } else {
            logger.warning("Lesson " + lesson.getSubject() + " wasn't removed");
        }
        return res;
    }


    @Override
    public List<ChangedLesson> getChanges(LocalDate date) {
        List<ChangedLesson> list = new ArrayList<>();
        KvList<ChangedLessonPOJO> response = client
                .listCollection(CHANGES)
                .limit(LIMIT)
                .get(ChangedLessonPOJO.class)
                .get(30, TimeUnit.SECONDS);
        for (KvObject<ChangedLessonPOJO> obj : response) {
            ChangedLessonPOJO pojo = obj.getValue();
            if (pojo.getDate().equals(Dates.FULL_DATE_FORMAT.format(date))) {
                logger.fine("Changed lesson was read: " + pojo);
                ChangedLesson changedLesson = ChangedLessonPOJO.parse(obj.getKey(), pojo);
                list.add(changedLesson);
            }
        }
        return list;
    }

    @Override
    public boolean addChange(ChangedLesson change) {
        if (change.getChanges() == LessonChanges.REMOVE) {
            KvList<ChangedLessonPOJO> response = client
                    .listCollection(CHANGES)
                    .limit(LIMIT)
                    .get(ChangedLessonPOJO.class)
                    .get(30, TimeUnit.SECONDS);
            for (KvObject<ChangedLessonPOJO> obj : response) {
                if (obj.getValue().getLessonId().equals(change.getLesson().getId().toString())) {
                    client.kv(CHANGES, obj.getKey()).delete();
                    return true;
                }
            }
        }
        KvMetadata metadata = client
                .kv(CHANGES, change.getId().toString())
                .put(ChangedLessonPOJO.of(change))
                .get(30, TimeUnit.SECONDS);
        logger.fine("Change was added: " + metadata.toString());
        return true;
    }

    @Override
    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        KvList<TaskPOJO> response = client
                .listCollection(TASKS)
                .limit(LIMIT)
                .get(TaskPOJO.class)
                .get(30, TimeUnit.SECONDS);
        for (KvObject<TaskPOJO> obj : response) {
            TaskPOJO pojo = obj.getValue();
            logger.fine("Task was read: " + pojo);
            Task task = new Task.Builder()
                    .id(UUID.fromString(obj.getKey()))
                    .subject(pojo.getSubject())
                    .type(LessonType.valueOf(pojo.getType()))
                    .deadline(FULL_DATE_FORMAT.parse(pojo.getDeadline()))
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
                .get(30, TimeUnit.SECONDS);
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
                .get(30, TimeUnit.SECONDS);
        if (res) {
            logger.fine("Task " + task.getTextTask() + " was removed");
        } else {
            logger.warning("Task " + task.getTextTask() + " wasn't removed");
        }
        return res;
    }
}