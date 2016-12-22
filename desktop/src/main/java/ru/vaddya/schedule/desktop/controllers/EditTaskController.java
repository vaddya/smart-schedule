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

import java.net.URL;
import java.util.ResourceBundle;

import static ru.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;

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
        subjectField.setText(task.getSubject());
        typeChoiceBox.setValue(task.getType());
        datePicker.setValue(task.getDateDeadline());
        textArea.setText(task.getText());
        doneCheckBox.setSelected(task.isDone());
        saved = false;
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent event) {
        task.setSubject(subjectField.getText());
        task.setType(typeChoiceBox.getValue());
        task.setDeadline(FULL_DATE_FORMAT.format(datePicker.getValue()));
        task.setText(textArea.getText());
        task.setDone(doneCheckBox.isSelected());
        saved = true;
        actionClose(event);
    }
}
