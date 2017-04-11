package com.vaddya.schedule.desktop.controllers;

import com.vaddya.schedule.desktop.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер для диалога удаления занятия
 *
 * @author vaddya
 */
public class RemoveLessonController implements Initializable {

    @FXML
    private Label sureLabel;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton onceRadioButton;
    @FXML
    private RadioButton alwaysRadioButton;

    private boolean isOnce;
    private boolean removed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.isOnce = true;
        this.removed = false;
        this.toggleGroup.selectToggle(onceRadioButton);
    }

    public void setSubject(String subject) {
        String content = String.format("%s:%n\"%s\"", Main.getString("lesson_remove_are_you_sure"), subject);
        this.sureLabel.setText(content);
        this.isOnce = true;
        this.removed = false;
        this.toggleGroup.selectToggle(onceRadioButton);
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionRemove(ActionEvent event) {
        isOnce = onceRadioButton.isSelected();
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