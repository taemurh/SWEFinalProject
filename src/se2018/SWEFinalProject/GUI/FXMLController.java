package se2018.SWEFinalProject.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FXMLController {
    @FXML 
    protected void handleAddStoryButtonAction(ActionEvent event) {
        System.out.println("Add Story button pressed!");
    }
    
    @FXML 
    protected void handleBacklogButtonAction(ActionEvent event) {
        System.out.println("Backlog button pressed!");
    }
    
    @FXML 
    protected void handleBurndownButtonAction(ActionEvent event) {
        System.out.println("Burndown button pressed!");
    }
}
