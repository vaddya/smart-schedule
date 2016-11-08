package ru.vaddya.schedule.core.tasks;

import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.db.Database;
import ru.vaddya.schedule.core.exceptions.NoSuchTaskException;
import ru.vaddya.schedule.core.utils.Dates;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для представления списка учебных заданий
 *
 * @author vaddya
 * @see Task
 */
public class StudyTasks {

    private static Database db = SmartSchedule.db();

    private List<Task> tasks;

    private static final Comparator<Task> DATE_ORDER = (t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline());

    public StudyTasks() {
        tasks = db.getTasks()
                .stream()
                .sorted(DATE_ORDER)
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int getSize() {
        return tasks.size();
    }

    /**
     * Добавить задание в список заданий
     *
     * @param task добавляемоое задание
     */
    public void addTask(Task task) {
        tasks.add(task);
        db.addTask(task);
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

    public Task findTask(int index) {
        return tasks.get(index);
    }

    public void completeTask(Task task) {
        Task upd = findTask(task.getId());
        upd.setComplete(true);
        db.updateTask(upd);
    }

    public void updateTask(Task task) {
        Task upd = findTask(task.getId());
        upd.setComplete(true);
        db.updateTask(upd);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getActiveTasks() {
        return tasks.stream()
                .filter((task) -> !task.isComplete())
                .collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isComplete)
                .collect(Collectors.toList());
    }

    public List<Task> getOverdueTasks() {
        return tasks.stream()
                .filter(task -> !task.isComplete())
                .filter(task -> Dates.isAfter(task.getDeadline()))
                .collect(Collectors.toList());
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Task task : tasks) {
            builder.append(i++)
                    .append(" | ")
                    .append(task);
        }
        return builder.toString();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void removeTask(int index) {
        tasks.remove(index-1);
    }
}
