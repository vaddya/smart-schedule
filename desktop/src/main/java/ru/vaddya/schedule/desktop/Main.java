package ru.vaddya.schedule.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Стартовый класс для графического приложения
 *
 * @author vaddya
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static ResourceBundle bundle;

    private static Image icon;

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static Image getIcon() {
        return icon;
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        bundle = ResourceBundle.getBundle("bundles/Locale", new Locale("ru"));
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));

        Parent root = fxmlLoader.load();
        primaryStage.setTitle(fxmlLoader.getResources().getString("smart_schedule"));
        icon = new Image(getClass().getClassLoader().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(600);
        primaryStage.setWidth(975);
        primaryStage.setHeight(800);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}