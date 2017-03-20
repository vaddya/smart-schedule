package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.lessons.LessonType;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.desktop.Main;
import com.vaddya.schedule.desktop.util.TypeTranslator;
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

/**
 * Контроллер для диалога изменения заданий
 *
 * @author vaddya
 */
public class EditTaskController implements Initializable {

    @FXML
    private TextField subjectField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea textArea;

    @FXML
    private CheckBox doneCheckBox;

    private UUID uuid;

    private Task task;

    private boolean saved;

    private boolean created;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Main.getBundle().getLocale());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (LessonType type : LessonType.values()) {
            typeChoiceBox.getItems().add(Main.getBundle().getString(type.toString().toLowerCase()));
        }
        datePicker.setOnShowing(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getBundle().getLocale()));
        datePicker.setOnShown(event -> Locale.setDefault(Locale.Category.FORMAT, Main.getBundle().getLocale()));
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
            uuid = UUID.randomUUID();
            subjectField.clear();
            typeChoiceBox.setValue(Main.getBundle().getString(ANOTHER.toString().toLowerCase()));
            datePicker.setValue(LocalDate.now());
            textArea.clear();
            doneCheckBox.setSelected(false);
        } else {
            created = false;
            uuid = task.getId();
            subjectField.setText(task.getSubject());
            typeChoiceBox.setValue(Main.getBundle().getString(task.getType().toString().toLowerCase()));
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
                .id(uuid)
                .subject(subjectField.getText())
                .type(TypeTranslator.parseLessonType(typeChoiceBox.getValue()))
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
