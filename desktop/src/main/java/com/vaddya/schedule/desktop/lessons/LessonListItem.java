package com.vaddya.schedule.desktop.lessons;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.desktop.util.TypeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.DayOfWeek;

/**
 * Вид для отображения занятия в списке
 *
 * @author vaddya
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
        initComponents(lesson);
    }

    private void initComponents(Lesson lesson) {
        timeLabel.setText(lesson.getStartTime().toString() + " - " + lesson.getEndTime().toString());
        subjectLabel.setText(lesson.getSubject());
        typeLabel.setText(TypeFormatter.format(lesson.getType()));
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