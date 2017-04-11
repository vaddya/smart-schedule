package com.vaddya.schedule.core;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.utils.Time;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * com.vaddya.schedule.core at smart-schedule
 *
 * @author vaddya
 * @since April 11, 2017
 */
public class LessonTest {

    @Test
    public void testEquals() throws Exception {
        UUID id = UUID.randomUUID();
        String strId = id.toString();
        Time start = Time.from("10:00");
        Time end = Time.from("11:30");
        String subject = "Subject";
        LessonType type = LessonType.LECTURE;
        String strType = type.toString();
        String place = "Place";
        String teacher = "Teacher";
        Lesson lesson1 = new Lesson.Builder()
                .id(id)
                .startTime(start)
                .endTime(end)
                .subject(subject)
                .type(type)
                .place(place)
                .teacher(teacher)
                .build();
        Lesson lesson2 = new Lesson.Builder()
                .id(strId)
                .startTime(start)
                .endTime(end)
                .subject(subject)
                .type(strType)
                .place(place)
                .teacher(teacher)
                .build();
        assertEquals(lesson1.getId(), lesson2.getId());
        assertEquals(lesson1.getStartTime(), lesson2.getStartTime());
        assertEquals(lesson1.getEndTime(), lesson2.getEndTime());
        assertEquals(lesson1.getSubject(), lesson2.getSubject());
        assertEquals(lesson1.getType(), lesson2.getType());
        assertEquals(lesson1.getPlace(), lesson2.getPlace());
        assertEquals(lesson1.getTeacher(), lesson2.getTeacher());
        assertEquals(lesson1, lesson2);
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

}