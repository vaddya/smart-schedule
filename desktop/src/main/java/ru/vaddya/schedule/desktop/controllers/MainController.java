package ru.vaddya.schedule.desktop.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.StatusBar;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;
import ru.vaddya.schedule.core.lessons.Lesson;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Контроллер для главного окна
 *
 * @author vaddya
 */
public class MainController implements Initializable {

    private SmartSchedule schedule;

    @FXML
    private LessonsController lessonsController;

    @FXML
    private TasksController tasksController;

    @FXML
    private StatusBar statusBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schedule = new SmartScheduleImpl();
        lessonsController.init(this, schedule);
        tasksController.init(this, schedule);
    }

    public List<String> getSubjectSuggestions() {
        return schedule.getCurrentWeek()
                .getAllDays()
                .entrySet()
                .stream()
                .flatMap(entrySet -> entrySet.getValue().getLessons().stream())
                .map(Lesson::getSubject)
                .distinct()
                .collect(Collectors.toList());
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setToStatusBar(String message) {
        statusBar.setText(message);
    }

    public void setToStatusBar(String message, int seconds) {
        statusBar.setText(message);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> statusBar.setText(""));
            }
        }, seconds * 1000);
    }

    public Stage showDialog(Window owner, Stage stage, Parent parent, String title) {
        if (stage == null) {
            stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
        }
        stage.setTitle(title);
        statusBar.setText(title);
        stage.showAndWait();
        statusBar.setText("");
        return stage;
    }
}