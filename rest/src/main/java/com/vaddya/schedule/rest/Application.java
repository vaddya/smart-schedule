package com.vaddya.schedule.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.SmartScheduleImpl;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyDay;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.mongo.MongoDatabase;
import com.vaddya.schedule.rest.seiralizers.LessonSerializer;
import com.vaddya.schedule.rest.seiralizers.StudyDaySerializer;
import com.vaddya.schedule.rest.seiralizers.StudyWeekSerializer;
import com.vaddya.schedule.rest.seiralizers.TaskSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * com.vaddya.schedule.rest at smart-schedule
 *
 * @author vaddya
 * @since April 05, 2017
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SmartSchedule schedule() {
        Database database = new MongoDatabase(new MongoClient());
        return new SmartScheduleImpl(database);
    }

    @Bean
    public Gson gson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Task.class, new TaskSerializer());
        builder.registerTypeAdapter(StudyWeek.class, new StudyWeekSerializer());
        builder.registerTypeAdapter(StudyDay.class, new StudyDaySerializer());
        builder.registerTypeAdapter(Lesson.class, new LessonSerializer());
        return builder.create();
    }

}