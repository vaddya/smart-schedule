package ru.vaddya.schedule.desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.tasks.TaskList;
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

    private TaskList taskList;

    private EditTaskController editTaskController;

    private Stage editTaskDialogStage;

    private Parent editTaskDialogParent;

    @FXML
    private Label tasksCountLabel;

    @FXML
    private TableView<TaskListItem> tasksTableView;

    @FXML
    private TableColumn<TaskListItem, String> taskStatusColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskSubjectColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskTypeColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskDeadlineColumn;

    @FXML
    private TableColumn<TaskListItem, String> taskTextColumn;

    public void init(MainController main, SmartSchedule schedule) {
        this.main = main;
        this.schedule = schedule;
        initControllers();
        initTasksTable();
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

    private void initTasksTable() {
        tasksTableView.setColumnResizePolicy(param -> false);

        taskStatusColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.145));
        taskSubjectColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.20));
        taskTypeColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.15));
        taskDeadlineColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.1));
        taskTextColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.4));

        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        taskSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        taskTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskTextColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        taskList = new TaskList(schedule.getTasks());
        taskList.addListener(c -> updateCountTasks());
        tasksTableView.setItems(taskList.getItems());
        updateCountTasks();

        tasksTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                TaskListItem task = tasksTableView.getSelectionModel().getSelectedItem();
                if (task != null) {
                    editTaskController.setActiveTask(task);
                    editTaskDialogStage = main.showDialog(tasksTableView.getScene().getWindow(),
                            editTaskDialogStage,
                            editTaskDialogParent,
                            Main.bundle.getString("task_edit")
                    );
                    if (editTaskController.isSaved()) taskList.update(task);
                }
            }
        });
    }

    public void actionButtonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        TaskListItem task;
        switch (button.getId()) {
            case "addTaskButton":
                task = new TaskListItem();
                editTaskController.setActiveTask(task);
                editTaskDialogStage = main.showDialog(tasksTableView.getScene().getWindow(),
                        editTaskDialogStage,
                        editTaskDialogParent,
                        Main.bundle.getString("task_add")
                );
                if (editTaskController.isSaved()) {
                    tasksTableView.getItems().add(task);
                }
                break;
            case "editTaskButton":
                task = tasksTableView.getSelectionModel().getSelectedItem();
                if (task == null) {
                    main.setToStatusBar(Main.bundle.getString("task_select_edit"), 5);
                } else {
                    editTaskController.setActiveTask(task);
                    editTaskDialogStage = main.showDialog(tasksTableView.getScene().getWindow(),
                            editTaskDialogStage,
                            editTaskDialogParent,
                            Main.bundle.getString("task_edit")
                    );
                    taskList.update(task);
                    tasksTableView.refresh();
                }
                break;
            case "removeTaskButton":
                task = tasksTableView.getSelectionModel().getSelectedItem();
                if (task == null) {
                    main.setToStatusBar(Main.bundle.getString("task_select_remove"), 5);
                } else {
                    task = tasksTableView.getSelectionModel().getSelectedItem();
                }
                tasksTableView.getItems().remove(task);
                break;
        }
    }

    private void updateCountTasks() {
        tasksCountLabel.setText(Main.bundle.getString("tasks_count") + " " + taskList.count());
    }
}
