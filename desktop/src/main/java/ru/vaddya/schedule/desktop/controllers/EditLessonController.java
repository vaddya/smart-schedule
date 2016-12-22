package ru.vaddya.schedule.desktop.controllers;

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
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.lessons.CreatedLesson;
import ru.vaddya.schedule.desktop.util.TypeTranslator;

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
    private RadioButton once;

    @FXML
    private RadioButton always;

    @FXML
    private ToggleGroup group;

    private UUID uuid;

    private CreatedLesson createdLesson;

    private boolean saved;

    private boolean created;

    private DayOfWeek sourceDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (LessonType type : LessonType.values()) {
            typeChoiceBox.getItems().add(Main.bundle.getString(type.toString().toLowerCase()));
        }
        typeChoiceBox.setValue(Main.bundle.getString("another"));
        for (DayOfWeek day : DayOfWeek.values()) {
            dayOfWeekChoiceBox.getItems().add(Main.bundle.getString(day.toString().toLowerCase()));
        }
        dayOfWeekChoiceBox.setValue(Main.bundle.getString("monday"));
        group.selectToggle(once);
    }

    public void setActiveLesson(Lesson lesson, DayOfWeek day) {
        if (lesson == null) {
            created = true;
            uuid = UUID.randomUUID();
            dayOfWeekChoiceBox.setValue(Main.bundle.getString(DayOfWeek.MONDAY.toString().toLowerCase()));
            timeStartField.clear();
            timeEndField.clear();
            subjectField.clear();
            typeChoiceBox.setValue(Main.bundle.getString(LessonType.ANOTHER.toString().toLowerCase()));
            placeField.clear();
            teacherField.clear();
        } else {
            sourceDay = day;
            created = false;
            uuid = lesson.getId();
            dayOfWeekChoiceBox.setValue(Main.bundle.getString(day.toString().toLowerCase()));
            timeStartField.setText(lesson.getStartTime().toString());
            timeEndField.setText(lesson.getEndTime().toString());
            subjectField.setText(lesson.getSubject());
            typeChoiceBox.setValue(Main.bundle.getString(lesson.getType().toString().toLowerCase()));
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
                .type(TypeTranslator.parseLessonType(typeChoiceBox.getValue()))
                .place(placeField.getText())
                .teacher(teacherField.getText())
                .build();
        createdLesson = new CreatedLesson(lesson, once.isSelected(), sourceDay,
                TypeTranslator.parseDayOfWeek(dayOfWeekChoiceBox.getValue()));
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