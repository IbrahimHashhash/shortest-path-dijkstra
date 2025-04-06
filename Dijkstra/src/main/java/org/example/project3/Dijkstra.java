package org.example.project3;

// Dijkstra class implementing Dijkstra's algorithm
public class Dijkstra {
    // method to initialize the table
    private static void initializeTable(SinglyLinkedList<String> vertices,HashTable<String, TableEntry> table,String start){
        for (int i = 0; i < vertices.size(); i++) {
            String vertex = vertices.get(i);
            table.put(vertex, new TableEntry());
        }
        table.get(start).weight = 0.0; // set the distance of the start vertex to 0
    }
    // method to execute Dijkstra algorithm on a grap
    public static SinglyLinkedList<String> dijkstra(Graph graph, String source, String target, String costType) {
        // initialize the table with all vertices and their corresponding table entries
        HashTable<String, TableEntry> table = new HashTable<>();
        SinglyLinkedList<String> vertices = graph.getVertexes();
        costType = costType.toLowerCase();
        initializeTable(vertices,table, source);
        while (true) {
            // find the smallest unknown distance vertex
            String currentVertex = smallestUnknownDistanceVertex(vertices, table);
            if (currentVertex == null || currentVertex.equals(target)) {
                break; // stop if no more unknown vertices or reached the end vertex
            }

            // mark the current vertex as known
            TableEntry currentEntry = table.get(currentVertex);
            currentEntry.known = true;

            // get the neighbors of the current vertex
            SinglyLinkedList<String> neighbors = graph.getAdjacentList().get(currentVertex);
            if (neighbors == null) continue; // skip if no neighbors

            // update the distances for each neighbor
            for (int i = 0; i < neighbors.size(); i++) {
                String neighbor = neighbors.get(i);
                String edgeKey = currentVertex + " to " + neighbor;
                FlightRoute edge = graph.getConnections().get(edgeKey);
                if (edge == null) continue; // skip if edge is missing

                // determine the cost based on the specified cost type
                double edgeCost = switch (costType) {
                    case "time" -> edge.getTime();
                    case "cost" -> edge.getCost();
                    default -> edge.getDistance();
                };

                // update the distance and previous vertex if a shorter path is found
                TableEntry neighborEntry = table.get(neighbor);
                double newDistance = currentEntry.weight + edgeCost;
                if (newDistance < neighborEntry.weight) {
                    neighborEntry.weight = newDistance;
                    neighborEntry.previousVertex = currentVertex;
                }
            }
        }

        // reconstruct the path from the end vertex to the start vertex
        SinglyLinkedList<String> path = new SinglyLinkedList<>();
        if (table.get(target).previousVertex == null) {
            return path; // return an empty path if no path is found
        }

        // trace back from the end vertex to the start vertex
        String currentVertex = target;
        while (currentVertex != null) {
            path.addFirst(currentVertex);
            currentVertex = table.get(currentVertex).previousVertex;
        }

        return path;
    }
    // method to find the vertex with the smallest unknown distance
    private static String smallestUnknownDistanceVertex(SinglyLinkedList<String> vertices, HashTable<String, TableEntry> table) {
        String smallestVertex = null;
        double smallestDistance = Double.MAX_VALUE;
        // iterate through all vertices and find the one with the smallest unknown distance
        for (int i = 0; i < vertices.size(); i++) {
            String vertex = vertices.get(i);
            TableEntry entry = table.get(vertex);
            if (!entry.known && entry.weight < smallestDistance) {
                smallestDistance = entry.weight;
                smallestVertex = vertex;
            }
        }

        return smallestVertex;
    }

    // method to calculate the total distance of a path
    public static double pathDistance(Graph graph, SinglyLinkedList<String> path) {
        return calculateTotal(graph, path, "distance");
    }

    // method to calculate the total time of a path
    public static double pathTime(Graph graph, SinglyLinkedList<String> path) {
        return calculateTotal(graph, path, "time");
    }

    // method to calculate the total cost of a path
    public static double pathCost(Graph graph, SinglyLinkedList<String> path) {
        return calculateTotal(graph, path, "cost");
    }

    // helper method to calculate the total cost, time, or distance of a path
    private static double calculateTotal(Graph graph, SinglyLinkedList<String> path, String costType) {
        if (path.isEmpty()) return 0; // return 0 if the path is empty

        double total = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            String edgeKey = from + " to " + to;
            FlightRoute edge = graph.getConnections().get(edgeKey);
            // add the cost based on the specified cost type
            switch (costType.toLowerCase()) {
                case "time":
                    total += edge.getTime();
                    break;
                case "cost":
                    total += edge.getCost();
                    break;
                case "distance":
                    total += edge.getDistance();
                    break;
            }
        }
        return total;
    }
}
