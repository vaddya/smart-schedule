package ru.vaddya.schedule.desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ru.vaddya.schedule.desktop.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ru.vaddya.schedule.desktop.controllers at smart-schedule
 *
 * @author vaddya
 * @since December 22, 2016
 */
public class RemoveLessonController implements Initializable {

    @FXML
    private Label are_you_sure;

    @FXML
    private RadioButton once;

    @FXML
    private RadioButton always;

    @FXML
    private ToggleGroup group;

    private boolean isOnce;

    private boolean removed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        are_you_sure.setTextAlignment(TextAlignment.CENTER);
        isOnce = true;
        removed = false;
        group.selectToggle(once);
    }

    public void setActiveLesson(String subject) {
        are_you_sure.setText(Main.bundle.getString("are_you_sure_remove_lesson") + "\n" + subject + "?");
        isOnce = true;
        removed = false;
        group.selectToggle(once);
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionRemove(ActionEvent event) {
        isOnce = once.isSelected();
        removed = true;
        actionClose(event);
    }

    public boolean isOnce() {
        return isOnce;
    }

    public boolean isRemoved() {
        return removed;
    }

}
