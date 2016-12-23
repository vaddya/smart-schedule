package ru.vaddya.schedule.desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.vaddya.schedule.core.lessons.LessonType;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.tasks.TaskListItem;
import ru.vaddya.schedule.desktop.util.TypeTranslator;

import java.net.URL;
import java.util.ResourceBundle;

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

    private TaskListItem task;

    private boolean saved = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (LessonType type : LessonType.values()) {
            typeChoiceBox.getItems().add(Main.bundle.getString(type.toString().toLowerCase()));
        }
        typeChoiceBox.setValue(Main.bundle.getString(LessonType.ANOTHER.toString().toLowerCase()));
    }

    public boolean isSaved() {
        return saved;
    }

    public void setActiveTask(TaskListItem task) {
        this.task = task;
        subjectField.setText(task.getTask().getSubject());
        typeChoiceBox.setValue(Main.bundle.getString(task.getTask().getType().toString().toLowerCase()));
        datePicker.setValue(task.getTask().getDeadline());
        textArea.setText(task.getTask().getTextTask());
        doneCheckBox.setSelected(task.getTask().isComplete());
        saved = false;
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent event) {
        task.getTask().setSubject(subjectField.getText());
        task.getTask().setType(TypeTranslator.parseLessonType(typeChoiceBox.getValue()));
        task.getTask().setDeadline(datePicker.getValue());
        task.getTask().setTextTask(textArea.getText());
        task.getTask().setComplete(doneCheckBox.isSelected());
        saved = true;
        actionClose(event);
    }
}
