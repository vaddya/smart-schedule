package com.vaddya.schedule.core.changes;

import com.vaddya.schedule.core.exceptions.NoSuchChangeException;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.database.ChangeRepository;

import java.time.LocalDate;
import java.util.*;

import static com.vaddya.schedule.core.changes.ChangeType.*;

/**
 * com.vaddya.schedule.core.changes at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
public class StudyChanges {

    private final ChangeRepository changes;

    public StudyChanges(ChangeRepository changes) {
        this.changes = changes;
    }

    public List<Change> findAll() {
        return changes.findAll();
    }

    public List<Change> findAll(LocalDate date) {
        return changes.findAll(date);
    }

    public Change addLesson(LocalDate date, Lesson lesson) {
        Change change = new Change(ADD, date, lesson);
        changes.insert(change);
        return change;
    }

    public Change updateLesson(LocalDate date, Lesson lesson) {
        Change change = new Change(UPDATE, date, lesson);
        changes.insert(change);
        return change;
    }

    public List<Change> changeLessonDate(LocalDate from, LocalDate to, Lesson lesson) {
        Change remove = new Change(REMOVE, from, lesson);
        changes.insert(remove);
        Change add = new Change(ADD, to, lesson);
        changes.insert(add);
        return Arrays.asList(remove, add);
    }

    public Change removeLesson(LocalDate date, Lesson lesson) {
        Change change = new Change(REMOVE, date, lesson);
        changes.insert(change);
        return change;
    }

    public List<Change> removeAllLessons(LocalDate date, List<Lesson> list) {
        List<Change> changeList = new ArrayList<>();
        for (Lesson lesson : list) {
            Change change = new Change(REMOVE, date, lesson);
            changeList.add(change);
            changes.insert(change);
        }
        return changeList;
    }

    public void addChange(Change change) {
        changes.insert(change);
    }

    public Change findChange(UUID id) {
        Optional<Change> optional = changes.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchChangeException(id);
    }

    public void updateChange(Change change) {
        changes.save(change);
    }

    public void removeChange(Change change) {
        changes.delete(change);
    }

    public void removeAllChanges() {
        changes.deleteAll();
    }

}