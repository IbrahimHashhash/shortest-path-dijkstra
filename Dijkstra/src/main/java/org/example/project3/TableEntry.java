package org.example.project3;

public class TableEntry {
     boolean known; // flag to indicate if the vertex is known
     double weight; // weight of the start vertex
     String previousVertex; // previous vertex in the path

    // constructor to initialize the table entry
    public TableEntry() {
        this.known = false;
        this.weight = Double.MAX_VALUE; // initialize distance to infinity
        this.previousVertex = null; // initialize previous vertex to null
    }
}
