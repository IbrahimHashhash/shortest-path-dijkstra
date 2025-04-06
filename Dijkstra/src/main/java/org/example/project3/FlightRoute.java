package org.example.project3;
public class FlightRoute {
    private double time;
    private double distance;
    private double cost;

    public FlightRoute(double distance, double cost, double time) {
        this.time = time;
        this.distance = distance;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Route{" +
                "time=" + time +
                ", distance=" + distance +
                ", cost=" + cost +
                '}';
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

