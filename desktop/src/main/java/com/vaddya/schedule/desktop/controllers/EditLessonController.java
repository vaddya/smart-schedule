package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.utils.Time;
import com.vaddya.schedule.core.utils.TypeOfWeek;
import com.vaddya.schedule.desktop.lessons.CreatedLesson;
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

import static com.vaddya.schedule.desktop.util.TypeConverters.*;

/**
 * Контроллер для диалога изменения занятий
 *
 * @author vaddya
 */
public class EditLessonController implements Initializable {

    @FXML
    private ChoiceBox<TypeOfWeek> typeOfWeekChoiceBox;
    @FXML
    private ChoiceBox<DayOfWeek> dayOfWeekChoiceBox;
    @FXML
    private TextField timeStartField;
    @FXML
    private TextField timeEndField;
    @FXML
    private TextField subjectField;
    @FXML
    private ChoiceBox<LessonType> lessonTypeChoiceBox;
    @FXML
    private TextField placeField;
    @FXML
    private TextField teacherField;
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
        typeOfWeekChoiceBox.setConverter(getTypeOfWeekConverter());
        typeOfWeekChoiceBox.getItems().addAll(TypeOfWeek.values());
        dayOfWeekChoiceBox.setConverter(getDayOfWeekConverter());
        dayOfWeekChoiceBox.getItems().addAll(DayOfWeek.values());
        lessonTypeChoiceBox.setConverter(getLessonTypeConverter());
        lessonTypeChoiceBox.getItems().addAll(LessonType.values());
        toggleGroup.selectToggle(alwaysRadioButton);
    }

    public void setActiveLesson(Lesson lesson, TypeOfWeek week, DayOfWeek day, List<String> suggestions) {
        if (lesson == null) {
            isCreated = true;
            id = UUID.randomUUID();
            dayOfWeekChoiceBox.setValue(DayOfWeek.MONDAY);
            timeStartField.clear();
            timeEndField.clear();
            subjectField.clear();
            lessonTypeChoiceBox.setValue(LessonType.ANOTHER);
            placeField.clear();
            teacherField.clear();
            typeOfWeekChoiceBox.setValue(TypeOfWeek.BOTH);
        } else {
            sourceDay = day;
            isCreated = false;
            id = lesson.getId();
            dayOfWeekChoiceBox.setValue(day);
            timeStartField.setText(lesson.getStartTime().toString());
            timeEndField.setText(lesson.getEndTime().toString());
            subjectField.setText(lesson.getSubject());
            lessonTypeChoiceBox.setValue(lesson.getType());
            placeField.setText(lesson.getPlace());
            teacherField.setText(lesson.getTeacher());
            typeOfWeekChoiceBox.setValue(week);
        }
        isSaved = false;
        TextFields.bindAutoCompletion(subjectField, suggestions);
    }

    public void actionSave(ActionEvent event) {
        isSaved = true;
        Lesson lesson = new Lesson.Builder()
                .id(id)
                .startTime(Time.from(timeStartField.getText()))
                .endTime(Time.from(timeEndField.getText()))
                .subject(subjectField.getText())
                .type(lessonTypeChoiceBox.getValue())
                .place(placeField.getText())
                .teacher(teacherField.getText())
                .build();
        createdLesson = new CreatedLesson(lesson, onceRadioButton.isSelected(), typeOfWeekChoiceBox.getValue(),
                sourceDay, dayOfWeekChoiceBox.getValue());
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