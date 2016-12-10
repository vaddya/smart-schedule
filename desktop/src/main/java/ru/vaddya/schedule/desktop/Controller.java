package ru.vaddya.schedule.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * ru.vaddya.schedule.desktop at smart-schedule
 *
 * @author vaddya
 * @since December 08, 2016
 */
public class Controller {

    @FXML
    private Button prevWeekButton;

    @FXML
    public void handleAction(ActionEvent event) {
        System.out.println("hey");
    }
}
