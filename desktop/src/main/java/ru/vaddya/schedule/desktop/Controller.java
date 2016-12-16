package ru.vaddya.schedule.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ru.vaddya.schedule.desktop at smart-schedule
 *
 * @author vaddya
 * @since December 08, 2016
 */
public class Controller implements Initializable {

    @FXML
    private Button prevWeekButton;

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

    private SmartSchedule schedule = new SmartScheduleImpl();

    @FXML
    public void handleAction(ActionEvent event) {
        System.out.println("hey");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        taskTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskTextColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        TaskList taskList = new TaskList(schedule.getTasks());
        tasksTableView.setItems(taskList.getItems());
    }
}
