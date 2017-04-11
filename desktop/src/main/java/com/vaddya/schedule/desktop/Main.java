package com.vaddya.schedule.desktop;

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

    private static final Locale locale = new Locale("ru");
    private static final ResourceBundle bundle = ResourceBundle.getBundle("bundles/Locale", locale);
    private static final Image icon = new Image(Main.class.getClassLoader().getResourceAsStream("img/icon.png"));

    public static Locale getLocale() {
        return locale;
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }

    public static Image getIcon() {
        return icon;
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setCharset(StandardCharsets.UTF_8);
        fxmlLoader.setResources(bundle);
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));

        Parent root = fxmlLoader.load();
        primaryStage.setTitle(fxmlLoader.getResources().getString("smart_schedule"));
        primaryStage.getIcons().add(icon);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(600);
        primaryStage.setWidth(850);
        primaryStage.setHeight(700);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}