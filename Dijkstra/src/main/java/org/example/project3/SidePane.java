package org.example.project3;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

// sidePane class extending VBox
public class SidePane extends VBox {
    private TextField distanceTextField = new TextField();
    private TextField costTextField = new TextField();
    private TextField timeTextField = new TextField();

    // constructor to initialize sidePane
    public SidePane() {
        Label sourceLabel = new Label("Source:");
        sourceLabel.setContentDisplay(ContentDisplay.RIGHT);
        sourceLabel.setGraphic(Manager.getSource());

        Label targetLabel = new Label("Target:");
        targetLabel.setContentDisplay(ContentDisplay.RIGHT);
        targetLabel.setGraphic(Manager.getTarget());

        Label filterLabel = new Label("Filter:");
        filterLabel.setContentDisplay(ContentDisplay.RIGHT);
        filterLabel.setGraphic(Manager.getFilter());

        Label pathLabel = new Label("Path:");
        pathLabel.setContentDisplay(ContentDisplay.BOTTOM);
        pathLabel.setGraphic(Manager.getPathArea());
        Manager.getPathArea().setWrapText(true);
        Label distanceLabel = new Label("Distance:");
        distanceTextField.setMaxWidth(100);
        distanceLabel.setContentDisplay(ContentDisplay.RIGHT);
        distanceLabel.setGraphic(distanceTextField);

        Label costLabel = new Label("Cost:");
        costLabel.setContentDisplay(ContentDisplay.RIGHT);
        costTextField.setMaxWidth(100);
        costLabel.setGraphic(costTextField);

        Label timeLabel = new Label("Time:");
        timeTextField.setMaxWidth(100);
        timeLabel.setContentDisplay(ContentDisplay.RIGHT);
        timeLabel.setGraphic(timeTextField);
        this.setAlignment(Pos.CENTER);
        timeTextField.setEditable(false);
        distanceTextField.setEditable(false);
        costTextField.setEditable(false);
        this.setSpacing(15);
        this.getChildren().addAll(sourceLabel, targetLabel, filterLabel, pathLabel, distanceLabel, costLabel, timeLabel);
    }

    // getters for text fields
    public TextField getDistanceTextField() {
        return distanceTextField;
    }

    public TextField getCostTextField() {
        return costTextField;
    }

    public TextField getTimeTextField() {
        return timeTextField;
    }
}
