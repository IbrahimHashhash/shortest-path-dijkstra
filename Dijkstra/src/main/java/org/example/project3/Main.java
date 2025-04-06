package org.example.project3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

// main class extending Application
public class Main extends Application {
    private HBox mainLayout = new HBox(10); // main layout container
    private Map pane = new Map(); // custom Map pane
    private Manager manager = new Manager(pane); // manager handling Map
    private SidePane sidePane = new SidePane(); // custom SidePane

    @Override
    public void start(Stage stage) {
        // set styles and alignment for layout and sidePane
        mainLayout.setStyle("-fx-background-color: rgba(0,64,72,255);");
        sidePane.setStyle("-fx-background-color: white;");
        mainLayout.setAlignment(Pos.CENTER);
        sidePane.setPrefWidth(273);

        // create and configure buttons
        Button run = getRun(manager, sidePane);
        Button reset = new Button("Clear Path");
        reset.setOnAction(e->{
            manager.getMap().getPath().clear();
            pane.clearPath();
        });

        Button chooseFile = new Button("Choose File");
        chooseFile.setOnAction(e->{
            handleBrowse();
        });

        // create and configure clear button
        Button clear = getClear();

        // add buttons to sidePane
        sidePane.getChildren().add(3, run);
        sidePane.getChildren().addAll(reset, clear, chooseFile);

        // set margins and add children to mainLayout
        HBox.setMargin(pane, new Insets(100, 50, 0, 20));
        HBox.setMargin(sidePane, new Insets(0, 0, 0, 0));
        mainLayout.getChildren().addAll(pane, sidePane);

        // create and configure scene
        Scene scene = new Scene(mainLayout, 1400, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // show stage and maximize
        stage.show();
        stage.setMaximized(true);
    }

    private Button getClear() {
        // create and configure clear button with confirmation dialog
        Button clear = new Button("Clear Map");
        clear.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to clear?");
            alert.setTitle("Confirm Clear");
            alert.setHeaderText(null);
            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                manager.clearAll();
                Alert success = new Alert(Alert.AlertType.INFORMATION, "All entries have been cleared.");
                success.setTitle("Cleared");
                sidePane.getTimeTextField().clear();
                sidePane.getCostTextField().clear();
                sidePane.getDistanceTextField().clear();
                success.setHeaderText(null);
                success.show();
            }
        });
        return clear;
    }

    private Button getRun(Manager manager, SidePane sidePane) {
        // create and configure run button
        Button run = new Button("Run");
        run.setOnAction(e->{
            if(manager.getMap().getPath().isEmpty()) {
                manager.start();
            } else {
                if(manager.getMap().getPath().size() == 2){
                String src = manager.getMap().getPath().get(0).getName();
                String target = manager.getMap().getPath().get(1).getName();
                Manager.getSource().setValue(src);
                Manager.getTarget().setValue(target);
                manager.start();
            }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a target");
                    alert.show();
                }
            }
            // update text fields in sidePane with results
            sidePane.getCostTextField().setText(Manager.getCost());
            sidePane.getDistanceTextField().setText(Manager.getDistance());
            sidePane.getTimeTextField().setText(Manager.getTime());
        });
        return run;
    }




    public void handleBrowse() {
        // open file chooser and read selected file
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            manager.readGraph(file);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
