package ru.vaddya.schedule.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.vaddya.schedule.core.SmartSchedule;
import ru.vaddya.schedule.core.SmartScheduleImpl;
import ru.vaddya.schedule.core.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * ru.vaddya.schedule.app at smart-schedule
 *
 * @author vaddya
 * @since December 05, 2016
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
        Pane root = new FlowPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        SmartSchedule schedule = new SmartScheduleImpl();
        List<Label> labels = new ArrayList<>();
        for (Task task : schedule.getTasks()) {
            labels.add(new Label(task.toString()));
        }
        root.getChildren().addAll(labels);
    }
}
