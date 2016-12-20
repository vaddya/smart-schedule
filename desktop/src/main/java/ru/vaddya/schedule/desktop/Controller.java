package ru.vaddya.schedule.desktop;

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
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ru.vaddya.schedule.desktop at smart-schedule
 *
 * @author vaddya
 * @since December 08, 2016
 */
public class Controller implements Initializable {

    private SmartSchedule schedule = new SmartScheduleImpl();

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

    @FXML
    public void handleAction(ActionEvent event) {
        System.out.println("hey");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tasksTableView.setColumnResizePolicy(param -> false);

        taskStatusColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.15));
        taskSubjectColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.25));
        taskTypeColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.1));
        taskDeadlineColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.1));
        taskTextColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.4));

        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        taskTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskTextColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        TaskList taskList = new TaskList(schedule.getTasks());
        tasksTableView.setItems(taskList.getItems());
        tasksCountLabel.setText("Всего заданий: " + taskList.count());

    }

    public void showDialog(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button button = (Button) source;

        switch (button.getId()) {
            case "addTaskButton":
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/add_task.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(button.getScene().getWindow());
                stage.show();
        }
    }
}
