package com.tickettoride.pricecalculatorservice;

import java.util.ArrayList;
import java.util.List;

public class City {
    String name;
    List<Neighbor> neighbors;

    public City(String name) {
        this.name = name;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(City neighbor, int segments) {
        this.neighbors.add(new Neighbor(neighbor, segments));
    }
}

class Neighbor {
    City city;
    int segments;

    Neighbor(City city, int segments) {
        this.city = city;
        this.segments = segments;
    }
}

class Route {
    City city;
    int segments;

    Route(City city, int segments) {
        this.city = city;
        this.segments = segments;
    }
}
