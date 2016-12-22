package ru.vaddya.schedule.desktop.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ru.vaddya.schedule.core.lessons.Lesson;
import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.desktop.lessons.CreatedLesson;

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
    private ChoiceBox<DayOfWeek> dayOfWeekChoiceBox;

    @FXML
    private TextField timeStartField;

    @FXML
    private TextField timeEndField;

    @FXML
    private TextField subjectField;

    @FXML
    private ChoiceBox<LessonType> typeChoiceBox;

    @FXML
    private TextField placeField;

    @FXML
    private TextField teacherField;

    @FXML
    private RadioButton once;

    @FXML
    private RadioButton always;

    @FXML
    private ToggleGroup group;

    private UUID uuid;

    private CreatedLesson createdLesson;

    private boolean saved;

    private boolean created;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dayOfWeekChoiceBox.setItems(FXCollections.observableArrayList(DayOfWeek.values()));
        typeChoiceBox.setItems(FXCollections.observableArrayList(LessonType.values()));
        group.selectToggle(once);
    }

    public void setActiveLesson(Lesson lesson, DayOfWeek day) {
        if (lesson == null) {
            created = true;
            uuid = UUID.randomUUID();
            dayOfWeekChoiceBox.setValue(DayOfWeek.MONDAY);
            timeStartField.clear();
            timeEndField.clear();
            subjectField.clear();
            typeChoiceBox.setValue(LessonType.ANOTHER);
            placeField.clear();
            teacherField.clear();
        } else {
            created = false;
            uuid = lesson.getId();
            dayOfWeekChoiceBox.setValue(day);
            timeStartField.setText(lesson.getStartTime().toString());
            timeEndField.setText(lesson.getEndTime().toString());
            subjectField.setText(lesson.getSubject());
            typeChoiceBox.setValue(lesson.getType());
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
        Lesson lesson = new Lesson.Builder()
                .id(uuid)
                .startTime(timeStartField.getText())
                .endTime(timeEndField.getText())
                .subject(subjectField.getText())
                .type(typeChoiceBox.getValue())
                .place(placeField.getText())
                .teacher(teacherField.getText())
                .build();
        createdLesson = new CreatedLesson(lesson, once.isSelected(), dayOfWeekChoiceBox.getValue());
        saved = true;
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
