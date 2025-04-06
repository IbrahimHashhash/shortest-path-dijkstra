package org.example.project3;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// manager class handling graph and map operations
public class Manager {
    private static ComboBox<String> sourceComboBox = new ComboBox<>();
    private static ComboBox<String> targetComboBox = new ComboBox<>();
    private static ComboBox<String> filterComboBox = new ComboBox<>();
    private static TextArea pathArea = new TextArea();
    private static String src, target, type, time, cost, distance;
    private static StringBuilder pathBuilder = new StringBuilder();
    private static Graph graph;
    private Map map;
    private static HashTable<String, CityNode> cityList = new HashTable<>();
    private static SinglyLinkedList<CityNode> pathList = new SinglyLinkedList<>();
    private int prev = -1;
    private int next = 0;
    private Alert error = new Alert(Alert.AlertType.ERROR);

    // constructor to initialize manager
    public Manager(Map map){
        this.map = map;
    }

    // method to read graph from a file
    public void readGraph(File file) {
        graph = new Graph();
        try (Scanner scanner = new Scanner(file)) {
            int numVertex = scanner.nextInt();
            int numEdges = scanner.nextInt();
            scanner.nextLine(); // move to the next line after reading integers

            // process vertices
            for (int i = 0; i < numVertex; i++) {
                if (!scanner.hasNextLine()) {
                    error.setContentText("Unexpected end of file while reading vertices.");
                    error.show();
                    return;
                }
                try {
                    String[] info = scanner.nextLine().split(",");
                    if (info.length < 3) {
                        throw new IllegalArgumentException("Vertex line has insufficient data: " + String.join(",", info));
                    }
                    String vertexName = info[0];
                    double latitude = Double.parseDouble(info[1]);
                    double longitude = Double.parseDouble(info[2]);

                    if (cityList.contains(vertexName)) {
                        error.setContentText("Duplicate vertex found: " + vertexName);
                        error.show();
                    } else {
                        CityNode cityNode = new CityNode(vertexName, latitude, longitude);
                        cityList.put(vertexName, cityNode);
                        map.addCity(cityNode);
                        graph.getVertexes().add(vertexName);
                        sourceComboBox.getItems().add(vertexName);
                        targetComboBox.getItems().add(vertexName);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping invalid vertex line: " + e.getMessage());
                }
            }

            // process edges
            for (int i = 0; i < numEdges; i++) {
                if (!scanner.hasNextLine()) {
                    error.setContentText("Unexpected end of file while reading edges.");
                    error.show();
                    return;
                }
                try {
                    String[] info = scanner.nextLine().split(",");
                    if (info.length < 4) {
                        throw new IllegalArgumentException("Edge line has insufficient data: " + String.join(",", info));
                    }
                    String capitalA = info[0];
                    String capitalB = info[1];
                    double cost = Double.parseDouble(info[2]);
                    double time = Double.parseDouble(info[3]);

                    if (!cityList.contains(capitalA) || !cityList.contains(capitalB)) {
                        throw new IllegalArgumentException("Edge references unknown vertex: " + capitalA + " or " + capitalB);
                    }

                    System.out.println("Edge Number: " + i + ": From: " + capitalA + " To: " + capitalB);
                    graph.addFlightRoute(cityList.get(capitalA), cityList.get(capitalB), time, cost);
                } catch (Exception e) {
                    System.out.println("Skipping invalid edge line at edge " + i + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            error.setContentText("File not found: " + e.getMessage());
            error.show();
        } catch (Exception e) {
            error.setContentText("An unexpected error occurred: " + e.getMessage());
            error.show();
        }
    }

    // method to get source comboBox
    public static ComboBox getSource(){
        sourceComboBox.setPromptText("Select Source");
        sourceComboBox.setOnAction(e->{
            src = sourceComboBox.getValue();
        });
        return sourceComboBox;
    }

    // method to get target comboBox
    public static ComboBox getTarget(){
        targetComboBox.setPromptText("Select Target");
        targetComboBox.setOnAction(e->{
            target = targetComboBox.getValue();
        });
        return targetComboBox;
    }

    // method to get path text area
    public static TextArea getPathArea(){
        pathArea.setMaxWidth(200);
        pathArea.setEditable(false);
        return pathArea;
    }

    // method to get filter comboBox
    public static ComboBox getFilter(){
        filterComboBox.setPromptText("Select Filter");
        filterComboBox.getItems().addAll("Time", "Cost", "Distance");
        filterComboBox.setOnAction(e-> type = filterComboBox.getValue());
        return filterComboBox;
    }

    // method to start the process
    public void start(){
        map.clearPath();
        pathBuilder.setLength(0);
        pathList.clear();
        prev = -1;
        next = 0;
        try{
            SinglyLinkedList<String> path = Dijkstra.dijkstra(graph, src, target, type);
            for (int i = 0; i < path.size(); i++) {
                pathBuilder.append(path.get(i));
                CityNode cityNode = cityList.get(path.get(i));
                pathList.add(cityNode);
                if (i < path.size() - 1) {
                    pathBuilder.append(" to ");
                }
            }
            pathArea.clear();
            pathArea.setText(pathBuilder.toString());
            cost = Dijkstra.pathCost(graph, path) + " $";
            distance = String.format("%.2f", Dijkstra.pathDistance(graph, path)) + " KM";
            time = Dijkstra.pathTime(graph, path) + " Min";
            drawPath();
        } catch (NullPointerException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incomplete input");
            alert.show();
        }
    }

    // getters for cost, time, and distance
    public static String getCost() {
        return cost;
    }

    public static String getTime() {
        return time;
    }

    public static String getDistance() {
        return distance;
    }

    // method to get the map
    public Map getMap() {
        return map;
    }

    // method to draw path on the map
    public void drawPath(){
        for(int i = 0; i < pathList.size() - 1; i++) {
            prev++;
            next++;
            double startX = map.longX(pathList.get(prev).getLongitude());
            double startY = map.latY(pathList.get(prev).getLatitude());
            double endX = map.longX(pathList.get(next).getLongitude());
            double endY = map.latY(pathList.get(next).getLatitude());

            // create the main line
            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.LIGHTGREEN);
            line.setStrokeWidth(2);

            // calculate the angle of the line
            double angle = Math.atan2(endY - startY, endX - startX);

            // length of the arrowhead
            double arrowLength = 10;

            // points for the arrowhead lines
            double arrowX1 = endX - arrowLength * Math.cos(angle - Math.PI / 6);
            double arrowY1 = endY - arrowLength * Math.sin(angle - Math.PI / 6);
            double arrowX2 = endX - arrowLength * Math.cos(angle + Math.PI / 6);
            double arrowY2 = endY - arrowLength * Math.sin(angle + Math.PI / 6);

            // create two lines for the arrowhead
            Line arrowHeadLine1 = new Line(endX, endY, arrowX1, arrowY1);
            Line arrowHeadLine2 = new Line(endX, endY, arrowX2, arrowY2);

            arrowHeadLine1.setStroke(Color.LIGHTGREEN);
            arrowHeadLine2.setStroke(Color.LIGHTGREEN);
            arrowHeadLine1.setStrokeWidth(2);
            arrowHeadLine2.setStrokeWidth(2);
            map.getChildren().addAll(line, arrowHeadLine1, arrowHeadLine2);
        }
    }

    // method to clear all data
    public void clearAll(){
        targetComboBox.getItems().clear();
        sourceComboBox.getItems().clear();
        pathList.clear();
        cityList.clear();
        pathBuilder.setLength(0);
        map.clearAll();
        pathArea.clear();
        prev = -1;
        next = 0;

    }
}
