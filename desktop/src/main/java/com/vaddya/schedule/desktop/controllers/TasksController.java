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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

/**
 * Контроллер для списка заданий
 *
 * @author vaddya
 */
public class TasksController {

    @FXML
    private ListView<Node> taskList;

    private MainController mainController;
    private EditTaskController editTaskController;
    private Stage editTaskDialogStage;
    private Parent editTaskDialogParent;

    private SmartSchedule schedule;

    public void init(MainController main, SmartSchedule schedule) {
        this.schedule = schedule;
        this.mainController = main;
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
                editTaskController.setActiveTask(task.getTask(), mainController.getSubjectSuggestions());
                editTaskDialogStage = mainController.showDialog(
                        taskList.getScene().getWindow(),
                        editTaskDialogStage,
                        editTaskDialogParent,
                        Main.getString("task_edit")
                );
                parseDialog();
            }
        });
        refreshTasks();
    }

    public void addTaskButtonPressed(ActionEvent event) {
        editTaskController.setActiveTask(null, mainController.getSubjectSuggestions());
        editTaskDialogStage = mainController.showDialog(
                taskList.getScene().getWindow(),
                editTaskDialogStage,
                editTaskDialogParent,
                Main.getString("task_add")
        );
        parseDialog();
    }

    public void editTasButtonPressed(ActionEvent event) {
        TaskListItem task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
        if (task == null) {
            mainController.setToStatusBar(Main.getString("task_select_edit"), 5);
        } else {
            editTaskController.setActiveTask(task.getTask(), mainController.getSubjectSuggestions());
            editTaskDialogStage = mainController.showDialog(
                    taskList.getScene().getWindow(),
                    editTaskDialogStage,
                    editTaskDialogParent,
                    Main.getString("task_edit")
            );
            parseDialog();
        }
    }

    public void removeTaskButtonPressed(ActionEvent event) {
        TaskListItem task = (TaskListItem) taskList.getSelectionModel().getSelectedItem();
        if (task == null) {
            mainController.setToStatusBar(Main.getString("task_select_remove"), 5);
        } else {
            Alert alert = new Alert(CONFIRMATION);
            alert.setTitle(Main.getString("task_remove"));
            alert.setHeaderText(null);
            String content = String.format("%s%n\"%s\"",
                    Main.getString("task_remove_are_you_sure"),
                    task.getTask().getTextTask()
            );
            alert.setContentText(content);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(Main.getIcon());
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                taskList.getItems().remove(task);
                schedule.getTasks().removeTask(task.getTask());
            }
        }
    }

    public void updateTask(Task task) {
        schedule.getTasks().updateTask(task);
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
        label.setText(Main.getString("tasks_count") + " " + (taskList.getItems().size() - 1));
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