package com.vaddya.schedule.desktop.tasks;

import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.core.utils.Dates;
import com.vaddya.schedule.desktop.controllers.TasksController;
import com.vaddya.schedule.desktop.util.TypeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;
import static com.vaddya.schedule.desktop.Main.getString;

/**
 * Вид для отображения задания в списке
 *
 * @author vaddya
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
    private Label timeLeftLabel;
    @FXML
    private Label typeLabel;

    private Task task;
    private TasksController controller;

    public TaskListItem(Task task, TasksController controller) {
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
        this.controller = controller;
        initComponents();
    }

    private void initComponents() {
        long daysUntil = Dates.daysUntil(task.getDeadline());
        String timeLeft;
        if (daysUntil == 0) {
            timeLeft = getString("today");
        } else if (daysUntil < 0) {
            timeLeft = String.format("%d %s", -daysUntil, getString("task_time_days_ago"));
        } else {
            timeLeft = String.format("%s %d %s", getString("task_time_in"), daysUntil,
                    getString("task_time_days"));
        }
        isDoneCheckBox.setSelected(task.isComplete());
        isDoneCheckBox.setOnAction(event -> {
            task.setComplete(isDoneCheckBox.isSelected());
            controller.updateTask(task);
        });
        if (task.isComplete()) {
            isDoneCheckBox.getStyleClass().add("complete");
        } else if (task.isOverdue()) {
            isDoneCheckBox.getStyleClass().add("overdue");
        }
        taskTextLabel.setText(task.getTextTask());
        if (task.isComplete()) {
            taskTextLabel.getStyleClass().add("complete");
        }
        subjectLabel.setText(task.getSubject());
        deadlineLabel.setText(FULL_DATE_FORMAT.format(task.getDeadline()));
        timeLeftLabel.setText("(" + timeLeft + ")");
        typeLabel.setText(TypeFormatter.format(task.getType()));
    }

    public Task getTask() {
        return task;
    }

}