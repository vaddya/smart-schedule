package com.vaddya.schedule.core.tasks;

import com.vaddya.schedule.core.exceptions.NoSuchTaskException;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.database.TaskRepository;

import java.util.*;
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

    private final List<Task> tasks;

    public StudyTasks(TaskRepository repository) {
        this.repository = repository;
        this.tasks = repository.findAllTasks()
                .stream()
                .sorted(COMPLETE_DATE_ORDER)
                .collect(Collectors.toList());
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
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /**
     * Добавить задание в список заданий
     */
    public void addTask(Task task) {
        tasks.add(task);
        tasks.sort(COMPLETE_DATE_ORDER);
        repository.addTask(task);
    }

    /**
     * Добавить все задания в список заданий
     */
    public void addAllTasks(Task... tasks) {
        Collections.addAll(this.tasks, tasks);
    }

    /**
     * Получить задание по ID
     *
     * @throws NoSuchTaskException если указан несуществующий ID
     */
    public Task findTask(UUID id) {
        Optional<Task> res = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
        if (res.isPresent()) {
            return res.get();
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
        Task upd = findTask(task.getId());
        tasks.set(tasks.indexOf(upd), task);
        tasks.sort(COMPLETE_DATE_ORDER);
        repository.updateTask(task);
    }

    /**
     * Удалить задание
     */
    public void removeTask(Task task) {
        repository.removeTask(task);
        tasks.remove(task);
    }

    /**
     * Удалить все задания
     */
    public void removeAllTasks() {
        for (Task task : tasks) {
            repository.removeTask(task);
        }
        tasks.clear();
    }

    /**
     * Получить все задания
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Получить только активные задания
     */
    public List<Task> getActiveTasks() {
        return tasks.stream()
                .filter(task -> !task.isComplete())
                .collect(Collectors.toList());
    }

    /**
     * Получить только выполненные задания
     */
    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isComplete)
                .collect(Collectors.toList());
    }

    /**
     * Получить только просроченные задания
     */
    public List<Task> getOverdueTasks() {
        return tasks.stream()
                .filter(task -> !task.isComplete())
                .filter(task -> !Dates.isAfter(task.getDeadline()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает строковое представление заданий
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
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
