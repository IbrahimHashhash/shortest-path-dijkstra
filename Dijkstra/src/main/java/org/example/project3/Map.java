package org.example.project3;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// map class extending Pane
public class Map extends Pane {
    private ImageView imageView;
    private static double mapWidth = 0;
    private static double mapHeight = 0;
    private SinglyLinkedList<CityNode> path = new SinglyLinkedList<>();

    // constructor to initialize the map
    public Map(){
        imageView = new ImageView(new Image("img_5.png"));
        mapWidth = 1250;
        imageView.setFitWidth(mapWidth);
        mapHeight = (mapWidth / 2560) * 1280;
        imageView.setFitHeight(mapHeight);
        this.setPrefHeight(mapHeight);
        this.setPrefWidth(mapWidth);

        imageView.setLayoutX((getPrefWidth() - mapWidth) / 2);
        imageView.setLayoutY((getPrefHeight() - mapHeight) / 2);

        this.getChildren().add(imageView);
    }

    // method to add a city to the map
    public void addCity(CityNode city){
        double x = longX(city.getLongitude());
        double y = latY(city.getLatitude());
        Pane cityPane = new Pane();
        Circle marker = new Circle(3, Color.RED);
        Text label = new Text(6,7, city.getName());
        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 7));
        label.setFill(Color.ORANGE);
        label.setOnMouseMoved(e -> {
            e.consume();  // Disable mouse move event
            marker.setRadius(3);marker.setFill(Color.RED);
            label.setY(6);label.setX(7);
            label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 7));
            label.setFill(Color.ORANGE);

        });
        label.setOnMouseClicked(e->{
            e.consume();  // Disable mouse move event
        });
        cityPane.getChildren().addAll(marker,label);
        cityPane.setOnMouseMoved(e->{
            marker.setRadius(6);marker.setFill(Color.ORANGE);
            label.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 16));
            label.setY(20);label.setX(15);
            label.setFill(Color.LIGHTGREEN);
        });
        cityPane.setOnMouseExited(e->{
            marker.setRadius(3);marker.setFill(Color.RED);
            label.setY(6);label.setX(7);
            label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 7));
            label.setFill(Color.ORANGE);
        });
        cityPane.setLayoutX(x);
        cityPane.setLayoutY(y);
        cityPane.setOnMouseClicked(e -> {
            if (path.size() < 2) {
                clearLines();
                path.add(city);
                if (path.size() == 2) {
                    Manager.getSource().setValue(path.get(0).getName());
                    Manager.getTarget().setValue(path.get(1).getName());
                    drawLine();
                }
            } else {
                clearPath();
                path.add(city);
            }
        });
        this.getChildren().add(cityPane);
    }

    // method to calculate x-coordinate from longitude
    public double longX(double longitude) {
        return mapWidth * (longitude + 180) / 360.0;
    }

    // method to calculate y-coordinate from latitude
    public double latY(double latitude) {
        return mapHeight * (90 - latitude) / 180.0;
    }

    // method to draw a line between two cities
    public void drawLine() {
        if (path.size() == 2) {
            double startX = longX(path.get(0).getLongitude());
            double startY = latY(path.get(0).getLatitude());
            double endX = longX(path.get(1).getLongitude());
            double endY = latY(path.get(1).getLatitude());

            // create the main line
            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.LIGHTGREEN);
            line.setStrokeWidth(2);

            // calculate the angle of the line
            double angle = Math.atan2(endY - startY, endX - startX);

            // length of the arrowhead
            double arrowLength = 10;

            // points for the arrowhead lines
            double arrowX1 = endX - arrowLength * Math.cos(angle - 0.5236);
            double arrowY1 = endY - arrowLength * Math.sin(angle - 0.5236);

            double arrowX2 = endX - arrowLength * Math.cos(angle + 0.5236);
            double arrowY2 = endY - arrowLength * Math.sin(angle + 0.5236);

            // create two lines for the arrowhead
            Line arrowHeadLine1 = new Line(endX, endY, arrowX1, arrowY1);
            Line arrowHeadLine2 = new Line(endX, endY, arrowX2, arrowY2);

            arrowHeadLine1.setStroke(Color.LIGHTGREEN);
            arrowHeadLine2.setStroke(Color.LIGHTGREEN);
            arrowHeadLine1.setStrokeWidth(2);
            arrowHeadLine2.setStrokeWidth(2);

            // add the main line and the arrowhead lines to the pane
            this.getChildren().addAll(line, arrowHeadLine1, arrowHeadLine2);
        }
    }

    // method to get the path
    public SinglyLinkedList<CityNode> getPath() {
        return path;
    }

    // method to clear all lines
    public void clearLines(){
        for(int i=this.getChildren().size() - 1;i>=0;i--){
            if(this.getChildren().get(i) instanceof Line){
                this.getChildren().remove(i);
            }
        }
    }

    // method to clear the path
    public void clearPath(){
        for(int i=this.getChildren().size() - 1;i>=0;i--){
            if(this.getChildren().get(i) instanceof Line){
                this.getChildren().remove(i);
            }
        }
        path.clear();
    }

    // method to clear all elements except the image
    public void clearAll(){
        for(int i=this.getChildren().size() - 1;i>=0;i--){
            if(!(this.getChildren().get(i) instanceof ImageView)){
                this.getChildren().remove(i);
            }
        }
        path.clear();
    }

}
