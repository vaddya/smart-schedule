package ru.vaddya.schedule.desktop;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * ru.vaddya.schedule.desktop at smart-schedule
 *
 * @author vaddya
 * @since December 20, 2016
 */
public class AddTaskController {

    public void closeAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
