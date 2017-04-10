package com.vaddya.schedule.core.tasks;

import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.database.TaskRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Класс для представления списка учебных заданий
 *
 * @author vaddya
 */
public class StudyTasks implements Iterable<Task> {

    private static final Comparator<Task> DATE_ORDER =
            (t1, t2) -> t1.isComplete()
                    ? -t1.getDeadline().compareTo(t2.getDeadline())
                    : t1.getDeadline().compareTo(t2.getDeadline());
    private static final Comparator<Task> COMPLETE_DATE_ORDER =
            comparing(Task::isComplete).thenComparing(DATE_ORDER);
    private final TaskRepository tasks;

    /**
     * Конструктор, принимающий хранилище задач
     */
    public StudyTasks(TaskRepository tasks) {
        this.tasks = tasks;
    }

    /**
     * Проверить пуст ли список заданий
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Получить количество заданий
     */
    public long getNumberOfTasks() {
        return tasks.size();
    }

    /**
     * Добавить задание в список заданий
     */
    public void addTask(Task task) {
        tasks.insert(task);
    }

    /**
     * Добавить все задания в список заданий
     */
    public void addAllTasks(Task... tasks) {
        Arrays.asList(tasks).forEach(this.tasks::insert);
    }

    /**
     * Получить задание по ID
     *
     * @throws NoSuchTaskException если указан несуществующий ID
     */
    public Task findTask(UUID id) {
        Optional<Task> task = tasks.findById(id);
        if (task.isPresent()) {
            return task.get();
        }
        throw new NoSuchTaskException(id);
    }

    /**
     * Обновить информацию о задании
     *
     * @throws NoSuchTaskException если указано несуществующее задание
     */
    public void updateTask(Task task) {
        tasks.save(task);
    }

    /**
     * Удалить задание
     */
    public void removeTask(Task task) {
        tasks.delete(task);
    }

    /**
     * Удалить все задания
     */
    public void removeAllTasks() {
        tasks.deleteAll();
    }

    /**
     * Получить все задания
     */
    public List<Task> getAllTasks() {
        return getFilteredTasks(task -> true);
    }

    /**
     * Получить только задания, удовлетворяющие условию
     */
    public List<Task> getFilteredTasks(Predicate<Task> predicate) {
        return tasks.findAll().stream()
                .filter(predicate)
                .sorted(COMPLETE_DATE_ORDER)
                .collect(Collectors.toList());
    }

    /**
     * Получить только активные задания
     */
    public List<Task> getActiveTasks() {
        return getFilteredTasks(task -> !task.isComplete());
    }

    /**
     * Получить только выполненные задания
     */
    public List<Task> getCompletedTasks() {
        return getFilteredTasks(Task::isComplete);
    }

    /**
     * Получить только просроченные задания
     */
    public List<Task> getOverdueTasks() {
        return getFilteredTasks(task -> !task.isComplete()
                && Dates.isPast(task.getDeadline()));
    }

    /**
     * Возвращает строковое представление заданий
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<Task> tasks = getAllTasks();
        int i = 1;
        for (Task task : tasks) {
            builder.append(i++)
                    .append(" | ")
                    .append(task)
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Возвращает итератор по заданиям
     */
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index = 0;
            private List<Task> tasks = getAllTasks();

            @Override
            public boolean hasNext() {
                return index < tasks.size();
            }

            @Override
            public Task next() {
                return tasks.get(index++);
            }
        };
    }

}