package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.tasks.Task;
import com.vaddya.schedule.desktop.Main;
import com.vaddya.schedule.desktop.tasks.TaskListItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

/**
 * Контроллер для списка заданий
 *
 * @author vaddya
 */
public class TasksController {

    private MainController main;

    private SmartSchedule schedule;

    private EditTaskController editTaskController;

    private Stage editTaskDialogStage;

    private Parent editTaskDialogParent;

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
        fxmlLoader.setResources(Main.getBundle());
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
        taskList.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                TaskListItem task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
                editTaskController.setActiveTask(task.getTask(), main.getSubjectSuggestions());
                editTaskDialogStage = main.showDialog(
                        taskList.getScene().getWindow(),
                        editTaskDialogStage,
                        editTaskDialogParent,
                        Main.getBundle().getString("task_edit")
                );
                parseDialog();
            }
        });
        refreshTasks();
    }

    private void refreshTasks() {
        taskList.getItems().clear();
        Label label = new Label();
        label.getStyleClass().add("title");
        label.setDisable(true);
        taskList.getItems().add(label);
        for (Task task : schedule.getTasks()) {
            taskList.getItems().add(new TaskListItem(task, this));
        }
        label.setText(Main.getBundle().getString("tasks_count") + " " + taskList.getItems().size());
    }

    public void actionButtonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        TaskListItem task;
        switch (button.getId()) {
            case "addTaskButton":
                editTaskController.setActiveTask(null, main.getSubjectSuggestions());
                editTaskDialogStage = main.showDialog(
                        taskList.getScene().getWindow(),
                        editTaskDialogStage,
                        editTaskDialogParent,
                        Main.getBundle().getString("task_add")
                );
                parseDialog();
                break;
            case "editTaskButton":
                task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
                if (task == null) {
                    main.setToStatusBar(Main.getBundle().getString("task_select_edit"), 5);
                } else {
                    editTaskController.setActiveTask(task.getTask(), main.getSubjectSuggestions());
                    editTaskDialogStage = main.showDialog(
                            taskList.getScene().getWindow(),
                            editTaskDialogStage,
                            editTaskDialogParent,
                            Main.getBundle().getString("task_edit")
                    );
                    parseDialog();
                }
                break;
            case "removeTaskButton":
                task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
                if (task == null) {
                    main.setToStatusBar(Main.getBundle().getString("task_select_remove"), 5);
                } else {
                    Alert alert = new Alert(CONFIRMATION);
                    alert.setTitle(Main.getBundle().getString("task_remove"));
                    alert.setHeaderText(null);
                    String content = String.format("%s: %n\"%s\"?",
                            Main.getBundle().getString("are_you_sure_remove_task"),
                            task.getTask().getTextTask()
                    );
                    alert.setContentText(content);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(Main.getIcon());
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        taskList.getItems().remove(task);
                        schedule.getTasks().removeTask(task.getTask());
                    }
                    break;
                }
        }
    }

    public void updateTask(Task task) {
        schedule.getTasks().updateTask(task);
        refreshTasks();
    }

    private void parseDialog() {
        if (editTaskController.isSaved()) {
            if (editTaskController.isCreated()) {
                schedule.getTasks().addTask(editTaskController.getTask());
            } else {
                schedule.getTasks().updateTask(editTaskController.getTask());
            }
            taskList.refresh();
            refreshTasks();
        }
    }
}
