package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.desktop.Main;
import com.vaddya.schedule.desktop.lessons.CreatedLesson;
import com.vaddya.schedule.desktop.util.TypeTranslator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Контроллер для диалога изменения занятий
 *
 * @author vaddya
 */
public class EditLessonController implements Initializable {

    @FXML
    private ChoiceBox<String> dayOfWeekChoiceBox;

    @FXML
    private TextField timeStartField;

    @FXML
    private TextField timeEndField;

    @FXML
    private TextField subjectField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private TextField placeField;

    @FXML
    private TextField teacherField;

    @FXML
    private RadioButton onceRadioButton;

    @FXML
    private RadioButton alwaysRadioButton;

    @FXML
    private ToggleGroup toggleGroup;

    private UUID uuid;

    private CreatedLesson createdLesson;

    private boolean saved;

    private boolean created;

    private DayOfWeek sourceDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (LessonType type : LessonType.values()) {
            typeChoiceBox.getItems().add(Main.getBundle().getString(type.toString().toLowerCase()));
        }
        for (DayOfWeek day : DayOfWeek.values()) {
            dayOfWeekChoiceBox.getItems().add(Main.getBundle().getString(day.toString().toLowerCase()));
        }
        toggleGroup.selectToggle(onceRadioButton);
    }

    public void setActiveLesson(Lesson lesson, DayOfWeek day) {
        if (lesson == null) {
            created = true;
            uuid = UUID.randomUUID();
            dayOfWeekChoiceBox.setValue(Main.getBundle().getString("monday"));
            timeStartField.clear();
            timeEndField.clear();
            subjectField.clear();
            typeChoiceBox.setValue(Main.getBundle().getString("another"));
            placeField.clear();
            teacherField.clear();
        } else {
            sourceDay = day;
            created = false;
            uuid = lesson.getId();
            dayOfWeekChoiceBox.setValue(Main.getBundle().getString(day.toString().toLowerCase()));
            timeStartField.setText(lesson.getStartTime().toString());
            timeEndField.setText(lesson.getEndTime().toString());
            subjectField.setText(lesson.getSubject());
            typeChoiceBox.setValue(Main.getBundle().getString(lesson.getType().toString().toLowerCase()));
            placeField.setText(lesson.getPlace());
            teacherField.setText(lesson.getTeacher());
        }
        saved = false;
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent event) {
        saved = true;
        Lesson lesson = new Lesson.Builder()
                .id(uuid)
                .startTime(timeStartField.getText())
                .endTime(timeEndField.getText())
                .subject(subjectField.getText())
                .type(TypeTranslator.parseLessonType(typeChoiceBox.getValue()))
                .place(placeField.getText())
                .teacher(teacherField.getText())
                .build();
        createdLesson = new CreatedLesson(lesson, onceRadioButton.isSelected(), sourceDay,
                TypeTranslator.parseDayOfWeek(dayOfWeekChoiceBox.getValue()));
        actionClose(event);
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean isCreated() {
        return created;
    }

    public CreatedLesson getCreatedLesson() {
        return createdLesson;
    }
}
