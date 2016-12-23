package ru.vaddya.schedule.desktop.tasks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.core.utils.Dates;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.controllers.TasksController;

import java.io.IOException;

import static ru.vaddya.schedule.core.utils.Dates.FULL_DATE_FORMAT;

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
        initComponents(task);
    }

    private void initComponents(Task task) {
        long daysUntil = Dates.daysUntil(task.getDeadline());
        String timeLeft;
        if (daysUntil == 0) {
            timeLeft = Main.getBundle().getString("task_time_today");
        } else if (daysUntil < 0) {
            timeLeft = -daysUntil + " " + Main.getBundle().getString("task_time_days_ago");
        } else {
            timeLeft = Main.getBundle().getString("task_time_in") + " " + daysUntil + " " +
                    Main.getBundle().getString("task_time_days");
        }
        isDoneCheckBox.setSelected(task.isComplete());
        isDoneCheckBox.setOnAction(event -> {
            task.setComplete(isDoneCheckBox.isSelected());
            controller.updateTask(task);
        });
        taskTextLabel.setText(task.getTextTask());
        subjectLabel.setText(task.getSubject());
        deadlineLabel.setText(FULL_DATE_FORMAT.format(task.getDeadline()));
        timeLeftLabel.setText("(" + timeLeft + ")");
        typeLabel.setText(Main.getBundle().getString(task.getType().toString().toLowerCase()));
    }

    public Task getTask() {
        return task;
    }
}
