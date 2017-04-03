package com.vaddya.schedule.core.tasks;

import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.database.TaskRepository;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Класс для представления списка учебных заданий
 *
 * @author vaddya
 * @see Task
 */
public class StudyTasks implements Iterable<Task> {

    private static final Comparator<Task> DATE_ORDER =
            (t1, t2) -> t1.isComplete()
                    ? -t1.getDeadline().compareTo(t2.getDeadline())
                    : t1.getDeadline().compareTo(t2.getDeadline());

    private static final Comparator<Task> COMPLETE_DATE_ORDER =
            Comparator.comparing(Task::isComplete).thenComparing(DATE_ORDER);

    private final TaskRepository repository;

    public StudyTasks(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Проверить пуст ли список заданий
     */
    public boolean isEmpty() {
        return repository.isEmpty();
    }

    /**
     * Получить количество заданий
     */
    public long getNumberOfTasks() {
        return repository.size();
    }

    /**
     * Добавить задание в список заданий
     */
    public void addTask(Task task) {
        repository.insert(task);
    }

    /**
     * Добавить все задания в список заданий
     */
    public void addAllTasks(Task... tasks) {
        for (Task task : tasks) {
            repository.insert(task);
        }
    }

    /**
     * Получить задание по ID
     *
     * @throws NoSuchTaskException если указан несуществующий ID
     */
    public Task findTask(UUID id) {
        Task task = repository.findById(id);
        if (task != null) {
            return task;
        } else {
            throw new NoSuchTaskException("Wrong task ID: " + id);
        }
    }

    /**
     * Получить задание по индексу
     *
     * @throws NoSuchTaskException если указан неверный индекс
     */
    public Task findTask(int index) {
        List<Task> tasks = getAllTasks();
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        } else {
            throw new NoSuchTaskException("Wrong task index: " + index +
                    ", Size: " + tasks.size());
        }
    }

    /**
     * Обновить информацию о задании
     *
     * @throws NoSuchTaskException если указано несуществующее задание
     */
    public void updateTask(Task task) {
        repository.save(task);
    }

    /**
     * Удалить задание
     */
    public void removeTask(Task task) {
        repository.delete(task);
    }

    /**
     * Удалить все задания
     */
    public void removeAllTasks() {
        repository.deleteAll();
    }

    /**
     * Получить все задания
     */
    public List<Task> getAllTasks() {
        return getTasksBy(task -> true);
    }

    /**
     * Получить только активные задания
     */
    public List<Task> getActiveTasks() {
        return getTasksBy(task -> !task.isComplete());
    }

    /**
     * Получить только выполненные задания
     */
    public List<Task> getCompletedTasks() {
        return getTasksBy(Task::isComplete);
    }

    /**
     * Получить только просроченные задания
     */
    public List<Task> getOverdueTasks() {
        return getTasksBy(task -> !task.isComplete() && !Dates.isAfter(task.getDeadline()));
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

    private List<Task> getTasksBy(Predicate<Task> predicate) {
        return repository.findAll().stream()
                .filter(predicate)
                .sorted(COMPLETE_DATE_ORDER)
                .collect(Collectors.toList());
    }
}