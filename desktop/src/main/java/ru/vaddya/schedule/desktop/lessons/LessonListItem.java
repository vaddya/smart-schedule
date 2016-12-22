package ru.vaddya.schedule.desktop.lessons;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.desktop.Main;

import java.io.IOException;
import java.time.DayOfWeek;

/**
 * ru.vaddya.schedule.desktop.lessons at smart-schedule
 *
 * @author vaddya
 * @since December 22, 2016
 */
public class LessonListItem extends AnchorPane {

    @FXML
    private Label timeLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label placeLabel;

    @FXML
    private Label teacherLabel;

    private Lesson lesson;

    private DayOfWeek day;

    public LessonListItem(Lesson lesson, DayOfWeek day) {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/lesson_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.lesson = lesson;
        this.day = day;
        timeLabel.setText(lesson.getStartTime().toString() + " - " + lesson.getEndTime().toString());
        subjectLabel.setText(lesson.getSubject());
        typeLabel.setText(Main.bundle.getString(lesson.getType().toString().toLowerCase()));
        placeLabel.setText(lesson.getPlace());
        teacherLabel.setText(lesson.getTeacher());
    }

    public Lesson getLesson() {
        return lesson;
    }

    public DayOfWeek getDay() {
        return day;
    }
}