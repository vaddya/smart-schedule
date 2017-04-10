package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.desktop.Main;
import com.vaddya.schedule.desktop.lessons.CreatedLesson;
import com.vaddya.schedule.desktop.util.TypeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.List;
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
    private ChoiceBox<String> typeOfWeekChoiceBox;
    @FXML
    private RadioButton alwaysRadioButton;
    @FXML
    private RadioButton onceRadioButton;
    @FXML
    private ToggleGroup toggleGroup;

    private UUID id;
    private CreatedLesson createdLesson;
    private boolean isSaved;
    private boolean isCreated;
    private DayOfWeek sourceDay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeChoiceBox.getItems().addAll(TypeFormatter.formatLessonTypes());
        dayOfWeekChoiceBox.getItems().addAll(TypeFormatter.formatDaysOfWeek());
        typeOfWeekChoiceBox.getItems().addAll(TypeFormatter.formatTypesOfWeek());
        toggleGroup.selectToggle(alwaysRadioButton);
    }

    public void setActiveLesson(Lesson lesson, TypeOfWeek week, DayOfWeek day, List<String> suggestions) {
        if (lesson == null) {
            isCreated = true;
            id = UUID.randomUUID();
            dayOfWeekChoiceBox.setValue(Main.getString("day_of_week_monday"));
            timeStartField.clear();
            timeEndField.clear();
            subjectField.clear();
            typeChoiceBox.setValue(Main.getString("lesson_type_another"));
            placeField.clear();
            teacherField.clear();
            typeOfWeekChoiceBox.setValue(Main.getString("type_of_week_both"));
        } else {
            sourceDay = day;
            isCreated = false;
            id = lesson.getId();
            dayOfWeekChoiceBox.setValue(TypeFormatter.format(day));
            timeStartField.setText(lesson.getStartTime().toString());
            timeEndField.setText(lesson.getEndTime().toString());
            subjectField.setText(lesson.getSubject());
            typeChoiceBox.setValue(TypeFormatter.format(lesson.getType()));
            placeField.setText(lesson.getPlace());
            teacherField.setText(lesson.getTeacher());
            typeOfWeekChoiceBox.setValue(TypeFormatter.format(week));
        }
        isSaved = false;
        TextFields.bindAutoCompletion(subjectField, suggestions);
    }

    public void actionSave(ActionEvent event) {
        isSaved = true;
        Lesson lesson = new Lesson.Builder()
                .id(id)
                .startTime(timeStartField.getText())
                .endTime(timeEndField.getText())
                .subject(subjectField.getText())
                .type(TypeFormatter.parseLessonType(typeChoiceBox.getValue()))
                .place(placeField.getText())
                .teacher(teacherField.getText())
                .build();
        createdLesson = new CreatedLesson(lesson, onceRadioButton.isSelected(),
                TypeFormatter.parseTypeOfWeek(typeOfWeekChoiceBox.getValue()),
                sourceDay,
                TypeFormatter.parseDayOfWeek(dayOfWeekChoiceBox.getValue()));
        actionClose(event);
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public boolean isSaved() {
        return isSaved;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public CreatedLesson getCreatedLesson() {
        return createdLesson;
    }

}