package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.desktop.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import static com.vaddya.schedule.core.lessons.LessonType.ANOTHER;
import static com.vaddya.schedule.desktop.util.TypeConverters.getLessonTypeConverter;

/**
 * Контроллер для диалога изменения заданий
 *
 * @author vaddya
 */
public class EditTaskController implements Initializable {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Main.getLocale());

    @FXML
    private TextField subjectField;
    @FXML
    private ChoiceBox<LessonType> lessonTypeChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea textArea;
    @FXML
    private CheckBox doneCheckBox;

    private UUID id;
    private Task task;
    private boolean saved;
    private boolean created;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lessonTypeChoiceBox.setConverter(getLessonTypeConverter());
        lessonTypeChoiceBox.getItems().addAll(LessonType.values());
        datePicker.setOnShowing(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getLocale()));
        datePicker.setOnShown(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getLocale()));
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return FORMATTER.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string, FORMATTER);
            }
        });
    }

    public void setActiveTask(Task task, List<String> suggestions) {
        if (task == null) {
            created = true;
            id = UUID.randomUUID();
            subjectField.clear();
            lessonTypeChoiceBox.setValue(ANOTHER);
            datePicker.setValue(LocalDate.now());
            textArea.clear();
            doneCheckBox.setSelected(false);
        } else {
            created = false;
            id = task.getId();
            subjectField.setText(task.getSubject());
            lessonTypeChoiceBox.setValue(task.getType());
            datePicker.setValue(task.getDeadline());
            textArea.setText(task.getTextTask());
            doneCheckBox.setSelected(task.isComplete());
        }
        saved = false;
        TextFields.bindAutoCompletion(subjectField, suggestions);
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent event) {
        saved = true;
        task = new Task.Builder()
                .id(id)
                .subject(subjectField.getText())
                .type(lessonTypeChoiceBox.getValue())
                .deadline(datePicker.getValue())
                .textTask(textArea.getText())
                .isComplete(doneCheckBox.isSelected())
                .build();
        actionClose(event);
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean isCreated() {
        return created;
    }

    public Task getTask() {
        return task;
    }

}