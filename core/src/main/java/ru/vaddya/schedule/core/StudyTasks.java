package ru.vaddya.schedule.core;

import ru.vaddya.schedule.core.io.Database;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для представления списка учебных заданий
 *
 * @author vaddya
 * @see Task
 */
public class StudyTasks {

    private Database db = Schedule.db();

    private List<Task> tasks;

    private static final Comparator<Task> DATE_ORDER = (t1, t2) -> t1.getDeadlineDate().compareTo(t2.getDeadlineDate());

    public StudyTasks() {
        tasks = db.getTasks().stream()
                .sorted(DATE_ORDER)
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int getSize() {
        return tasks.size();
    }

    public void add(Task task) {
        db.addTask(task);
        tasks.add(task);
    }

    public Task get(String id) {
        Optional<Task> res = tasks.stream()
                .filter((task -> id.equals(task.getId())))
                .findFirst();
        return res.isPresent() ? res.get() : null;
    }

    public List<Task> getAll() {
        return tasks.stream()
                .collect(Collectors.toList());
    }

    public List<Task> getActive() {
        return tasks.stream()
                .filter((task) -> !task.isComplete())
                .collect(Collectors.toList());
    }

    public List<Task> getCompleted() {
        return tasks.stream()
                .filter(Task::isComplete)
                .collect(Collectors.toList());
    }

    public List<Task> getOverdue() {
        return tasks.stream()
                .filter(task -> !task.isComplete())
                .filter(task -> new Date().after(task.getDeadlineDate()))
                .collect(Collectors.toList());
    }

    public void remove(Task task) {
        db.removeTask(task);
        tasks.remove(task);
    }

    public void update(Task task) {
        db.updateTask(task);
    }
}
