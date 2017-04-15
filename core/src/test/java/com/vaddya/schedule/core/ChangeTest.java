package com.vaddya.schedule.core;

import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.changes.ChangeType;
import com.vaddya.schedule.core.lessons.Lesson;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * com.vaddya.schedule.core at smart-schedule
 *
 * @author vaddya
 * @since April 12, 2017
 */
public class ChangeTest {

    @Test
    public void testEquals() throws Exception {
        UUID id = UUID.randomUUID();
        ChangeType type = ChangeType.REMOVE;
        LocalDate date = LocalDate.now();
        Lesson lesson = new Lesson.Builder().build();

        Change change1 = new Change(id, type, date, lesson);
        Change change2 = new Change(id, type, date, lesson);

        assertEquals(change1.getId(), change2.getId());
        assertEquals(change1.getChangeType(), change2.getChangeType());
        assertEquals(change1.getDate(), change2.getDate());
        assertEquals(change1.getLesson(), change2.getLesson());
        assertEquals(change1, change2);
        assertEquals(change1.hashCode(), change2.hashCode());
    }

}