package ru.vaddya.schedule.desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;
import ru.vaddya.schedule.desktop.Main;
import ru.vaddya.schedule.desktop.tasks.TaskList;
import ru.vaddya.schedule.desktop.tasks.TaskListItem;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

/**
 * Контроллер для главного окна
 *
 * @author vaddya
 */
public class MainController implements Initializable {

    private SmartSchedule schedule = new SmartScheduleImpl();

    private TaskList taskList;

    private EditTaskController editTaskController;

    private Stage taskEditDialogStage;

    private Parent taskEditDialogParent;

    @FXML
    private Button prevWeekButton;

    @FXML
    private Button addTaskButton;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tasksTableView.setColumnResizePolicy(param -> false);

        taskStatusColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.15));
        taskSubjectColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.25));
        taskTypeColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.1));
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

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(Main.bundle);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/edit_task.fxml"));
        try {
            taskEditDialogParent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editTaskController = fxmlLoader.getController();

        tasksTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                TaskListItem task = tasksTableView.getSelectionModel().getSelectedItem();
                editTaskController.setActiveTask(task);
                showTaskDialog(tasksTableView.getScene().getWindow());
                taskList.update(task);
            }
        });
    }

    public void actionButtonPressed(ActionEvent event) {
        Object source = event.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button button = (Button) source;
        TaskListItem task;
        switch (button.getId()) {
            case "addTaskButton":
                task = new TaskListItem();
                editTaskController.setActiveTask(task);
                showTaskDialog(button.getScene().getWindow());
                tasksTableView.getItems().add(task);
                break;
            case "editTaskButton":
                task = tasksTableView.getSelectionModel().getSelectedItem();
                editTaskController.setActiveTask(task);
                showTaskDialog(tasksTableView.getScene().getWindow());
                taskList.update(task);
                tasksTableView.refresh();
                break;
            case "removeTaskButton":
                task = tasksTableView.getSelectionModel().getSelectedItem();
                tasksTableView.getItems().remove(task);
                break;
        }
    }

    private void updateCountTasks() {
        tasksCountLabel.setText(Main.bundle.getString("tasks_count") + " " + taskList.count());
    }

    private void showTaskDialog(Window window) {
        if (taskEditDialogStage == null) {
            taskEditDialogStage = new Stage();
            taskEditDialogStage.setTitle(Main.bundle.getString("task_add"));
            taskEditDialogStage.setResizable(false);
            taskEditDialogStage.setScene(new Scene(taskEditDialogParent));
            taskEditDialogStage.initModality(Modality.WINDOW_MODAL);
            taskEditDialogStage.initOwner(window);
        }
        taskEditDialogStage.showAndWait();
    }
}
