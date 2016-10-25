package ru.vaddya.schedule.core.tasks;

import ru.vaddya.schedule.core.exceptions.NoSuchTaskException;
import ru.vaddya.schedule.core.Schedule;
import ru.vaddya.schedule.core.db.Database;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для представления списка учебных заданий
 *
 * @author vaddya
 * @see Task
 */
public class StudyTasks {

    private static Database db = Schedule.db();

    private List<Task> tasks;

    private static final Comparator<Task> DATE_ORDER = (t1, t2) -> t1.getDeadlineDate().compareTo(t2.getDeadlineDate());

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

    public void addTask(Task task) {
        tasks.add(task);
        db.addTask(task);
    }

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
                .filter(task -> new Date().after(task.getDeadlineDate()))
                .collect(Collectors.toList());
    }

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
}
