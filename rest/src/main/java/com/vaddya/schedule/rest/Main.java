package com.vaddya.schedule.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.SmartScheduleImpl;
import com.vaddya.schedule.core.changes.Change;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.StudyWeek;
import com.vaddya.schedule.core.schedule.ScheduleDay;
import com.vaddya.schedule.core.schedule.ScheduleWeek;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.dynamo.DynamoDatabase;
import com.vaddya.schedule.rest.seiralizers.*;
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
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public SmartSchedule schedule() {
        /* MongoDB */
//        MongoClientURI uri = new MongoClientURI(System.getenv("MONGODB_URI"));
//        MongoClient client = new MongoClient(uri);
//        Database database = new MongoDatabase(client);

        /* DynamoDB */
        Database database = new DynamoDatabase();
        return new SmartScheduleImpl(database);
    }

    @Bean
    public Gson gson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Task.class, new TaskSerializer());
        builder.registerTypeAdapter(ScheduleWeek.class, new StudyWeekSerializer());
        builder.registerTypeAdapter(ScheduleDay.class, new StudyDaySerializer());
        builder.registerTypeAdapter(Lesson.class, new LessonSerializer());
        builder.registerTypeAdapter(StudyWeek.class, new LessonsWeekSerializers());
        builder.registerTypeAdapter(Change.class, new ChangeSerializer());
        return builder.create();
    }

}