package ru.vaddya.schedule.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Стартовый класс для консольного приложения
 *
 * @author vaddya
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getClassLoader().getResource("fxml/root.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Smart Schedule");
        primaryStage.setMinWidth(575);
        primaryStage.setMinHeight(600);
        primaryStage.setWidth(975);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}