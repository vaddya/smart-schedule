package ru.vaddya.schedule.desktop.tasks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.desktop.Main;

import java.io.IOException;

/**
 * ru.vaddya.schedule.desktop.tasks at smart-schedule
 *
 * @author vaddya
 * @since December 23, 2016
 */
public class TaskListItem extends AnchorPane {

    @FXML
    private CheckBox isDoneCheckBox;

    @FXML
    private Label taskTextLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label deadlineLabel;

    @FXML
    private Label typeLabel;

    private Task task;

    public TaskListItem(Task task) {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/task_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.task = task;
        isDoneCheckBox.setSelected(task.isComplete());
        taskTextLabel.setText(task.getTextTask());
        subjectLabel.setText(task.getSubject());
        deadlineLabel.setText(Dates.FULL_DATE_FORMAT.format(task.getDeadline()));
        typeLabel.setText(Main.bundle.getString(task.getType().toString().toLowerCase()));
    }

    public Task getTask() {
        return task;
    }
}
