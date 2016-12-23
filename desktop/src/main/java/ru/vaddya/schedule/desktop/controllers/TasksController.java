package ru.vaddya.schedule.desktop.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.tasks.Task;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.tasks.TaskListItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * ru.vaddya.schedule.desktop.controllers at smart-schedule
 *
 * @author vaddya
 * @since December 23, 2016
 */
public class TasksController {

    private MainController main;

    private SmartSchedule schedule;

    private EditTaskController editTaskController;

    private Stage editTaskDialogStage;

    private Parent editTaskDialogParent;

    @FXML
    private Label tasksCountLabel;

    @FXML
    private ListView<Node> taskList;

    public void init(MainController main, SmartSchedule schedule) {
        this.main = main;
        this.schedule = schedule;
        initControllers();
        initLessonsList();
    }

    private void initControllers() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.bundle);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/edit_task.fxml"));
        try {
            editTaskDialogParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editTaskController = fxmlLoader.getController();
    }


    private void initLessonsList() {
        taskList.setFocusTraversable(false);
        taskList.getItems().addListener((ListChangeListener<Node>) c -> updateCountTasks());
        taskList.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                TaskListItem task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
                editTaskController.setActiveTask(task);
                editTaskDialogStage = main.showDialog(taskList.getScene().getWindow(),
                        editTaskDialogStage,
                        editTaskDialogParent,
                        Main.bundle.getString("task_edit")
                );
                schedule.getTasks().updateTask(task.getTask());
                taskList.refresh();
                refreshTasks();
            }
        });
        refreshTasks();
    }

    private void refreshTasks() {
        taskList.getItems().clear();
        Label label = new Label();
        label.setDisable(true);
        taskList.getItems().add(label);
        for (Task task : schedule.getTasks()) {
            taskList.getItems().add(new TaskListItem(task));
        }
        label.setText("Задания (" + Main.bundle.getString("tasks_count") + " " + taskList.getItems().size() + ")");
    }

    public void actionButtonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        TaskListItem task;
        switch (button.getId()) {
            case "addTaskButton":
//                task = new TaskListItem();
//                editTaskController.setActiveTask(task);
//                editTaskDialogStage = main.showDialog(tasksTableView.getScene().getWindow(),
//                        editTaskDialogStage,
//                        editTaskDialogParent,
//                        Main.bundle.getString("task_add")
//                );
//                if (editTaskController.isSaved()) {
//                    tasksTableView.getItems().add(task);
//                }
//                break;
            case "editTaskButton":
                task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
                if (task == null) {
                    main.setToStatusBar(Main.bundle.getString("task_select_edit"), 5);
                } else {
                    editTaskController.setActiveTask(task);
                    editTaskDialogStage = main.showDialog(taskList.getScene().getWindow(),
                            editTaskDialogStage,
                            editTaskDialogParent,
                            Main.bundle.getString("task_edit")
                    );
                    schedule.getTasks().updateTask(task.getTask());
                    taskList.refresh();
                    refreshTasks();
                }
                break;
            case "removeTaskButton":
//                task = tasksTableView.getSelectionModel().getSelectedItem();
//                if (task == null) {
//                    main.setToStatusBar(Main.bundle.getString("task_select_remove"), 5);
//                } else {
//                    task = tasksTableView.getSelectionModel().getSelectedItem();
//                }
//                tasksTableView.getItems().remove(task);
//                break;
        }
    }

    private void updateCountTasks() {
        tasksCountLabel.setText("");
    }
}
