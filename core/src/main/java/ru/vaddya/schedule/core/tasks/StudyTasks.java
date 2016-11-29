package ru.vaddya.schedule.core.tasks;

import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.exceptions.NoSuchTaskException;
import ru.vaddya.schedule.core.utils.Dates;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для представления списка учебных заданий
 *
 * @author vaddya
 * @see Task
 */
public class StudyTasks implements Iterable<Task> {

    private static final Database db = Database.getConnection();

    private static final Comparator<Task> DATE_ORDER = Comparator.comparing(Task::getDeadline);

    private final List<Task> tasks;

    public StudyTasks() {
        tasks = db.getTasks()
                .stream()
                .sorted(DATE_ORDER)
                .collect(Collectors.toList());
    }

    /**
     * Получить количество заданий
     *
     * @return количество заданий
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /**
     * Добавить задание в список заданий
     *
     * @param task добавляемоое задание
     */
    public void addTask(Task task) {
        tasks.add(task);
        tasks.sort(DATE_ORDER);
        db.addTask(task);
    }

    public void addAllTasks(Task... tasks) {
        Collections.addAll(this.tasks, tasks);
    }

    /**
     * Получить задание по ID
     *
     * @param id UUID задания
     * @return запрашиваемое задание
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
     * @param index порядковый номер задания
     * @return запрашиваемое задание
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
     * @param task выолненное задание
     * @throws NoSuchTaskException если указано несуществующее задание
     */
    public void updateTask(Task task) {
        Task upd = findTask(task.getId());
        tasks.set(tasks.indexOf(upd), task);
        tasks.sort(DATE_ORDER);
        db.updateTask(task);
    }

    /**
     * Удалить задание
     *
     * @param task удаляемое задание
     */
    public void removeTask(Task task) {
        db.removeTask(task);
        tasks.remove(task);
    }

    /**
     * Получить все задания
     *
     * @return все задания
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Получить только активные задания
     *
     * @return активные задания
     */
    public List<Task> getActiveTasks() {
        return tasks.stream()
                .filter(task -> !task.isComplete())
                .collect(Collectors.toList());
    }

    /**
     * Получить только выполненные задания
     *
     * @return выполненные задания
     */
    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isComplete)
                .collect(Collectors.toList());
    }

    /**
     * Получить только просроченные задания
     *
     * @return просроченные задания
     */
    public List<Task> getOverdueTasks() {
        return tasks.stream()
                .filter(task -> !task.isComplete())
                .filter(task -> !Dates.isAfter(task.getDeadline()))
                .collect(Collectors.toList());
    }

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
                return tasks.get(index);
            }
        };
    }
}
