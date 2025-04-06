package org.example.project3;

import javafx.scene.control.Alert;

// graph class handling edges and vertices
public class Graph {
    // hash table to store the adjacency list of the graph
    private HashTable<String, SinglyLinkedList<String>> adjacentList = new HashTable<>();

    // hash table to store the edges of the graph
    private HashTable<String, FlightRoute> connections = new HashTable<>();

    // list to store the vertices of the graph
    private SinglyLinkedList<String> vertexes = new SinglyLinkedList<>();

    // alert for warning messages
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    // constructor to initialize graph
    public Graph(){
    }

    // method to add an edge between two cities
    private boolean checkIfDirect(String from, String to){
        String edge = to + " to " + from;
        return connections.contains(edge);
    }
    public void addFlightRoute(CityNode from, CityNode to, double time, double cost) {
        double distance = distance(from, to);
        FlightRoute edge = new FlightRoute(distance, cost, time);
        System.out.println("Edge created: " + edge);

        // add the edge to the edges table
        connections.put(from.getName() + " to " + to.getName(), edge);

        // get the adjacency list for the "from" city
        SinglyLinkedList<String> adjacency = adjacentList.get(from.getName());

        // initialize the LinkedList for adjacency if not present
        if (adjacency == null) {
            adjacency = new SinglyLinkedList<>();
            adjacentList.put(from.getName(), adjacency);
        }

        // add the "to" city to the adjacency list if not already present
        if (!adjacency.contains(to.getName()) && !checkIfDirect(from.getName(), to.getName())) {
            adjacency.add(to.getName());
        } else {
            // show an alert if the edge is a duplicate
            alert.setContentText("Duplicate edge ignored: " + from.getName() + " to " + to.getName());
            alert.show();
        }

        // optionally, add the vertices if they don't already exist
        if (!vertexes.contains(from.getName())) {
            vertexes.add(from.getName());
        }
        if (!vertexes.contains(to.getName())) {
            vertexes.add(to.getName());
        }
    }

    // method to calculate the distance between two cities
    public double distance(CityNode src, CityNode dest) {
        double lat1 = Math.toRadians(src.getLatitude());
        double lon1 = Math.toRadians(src.getLongitude());
        double lat2 = Math.toRadians(dest.getLatitude());
        double lon2 = Math.toRadians(dest.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double sinLat = Math.sin(dlat / 2);
        double sinLon = Math.sin(dlon / 2);

        double a = (sinLat * sinLat) + Math.cos(lat1) * Math.cos(lat2) * (sinLon * sinLon);
        double c = 2 * Math.asin(Math.min(1.0, Math.sqrt(a)));

        // return the distance in kilometers
        return 6371 * c;
    }

    // getters for adjacent list, edges, and vertices
    public HashTable<String, SinglyLinkedList<String>> getAdjacentList() {
        return adjacentList;
    }

    public HashTable<String, FlightRoute> getConnections() {
        return connections;
    }

    public SinglyLinkedList<String> getVertexes() {
        return vertexes;
    }
}
