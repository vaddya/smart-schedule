package com.vaddya.schedule.desktop.controllers;

import com.mongodb.MongoClient;
import com.vaddya.schedule.core.SmartSchedule;
import com.vaddya.schedule.core.SmartScheduleImpl;
import com.vaddya.schedule.core.lessons.Lesson;
import com.vaddya.schedule.database.Database;
import com.vaddya.schedule.database.mongo.MongoDatabase;
import com.vaddya.schedule.desktop.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.StatusBar;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Main.getBundle().getLocale());

    @FXML
    private LessonsController lessonsController;

    @FXML
    private TasksController tasksController;

    @FXML
    private StatusBar statusBar;

    @FXML
    private Menu currDateMenu;

    private SmartSchedule schedule;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MongoClient client = new MongoClient();
        Database database = new MongoDatabase(client);
        schedule = new SmartScheduleImpl(database);
        lessonsController.init(this, schedule);
        tasksController.init(this, schedule);
        currDateMenu.setText(Main.getBundle().getString("today") + " " + LocalDate.now().format(FORMATTER));
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
        stage.getIcons().add(Main.getIcon());
        statusBar.setText(title);
        stage.showAndWait();
        statusBar.setText("");
        return stage;
    }
}