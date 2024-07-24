package com.tickettoride.pricecalculatorservice;

import java.util.*;

/**
 * Сlass for finding the most optimal route between the cities.
 * Uses Dijkstra's algorithm to find the shortest path and calculates the cost of the route.
 */
public class OptimalRouteFinder {

    /**
     * Stores cities from City.class with their String name.
     * Used to be able to put String value for departure and arrival city.
     */
    private static Map<String, City> cities = new HashMap<>();


    /**
     * Вычисляет стоимость маршрута в зависимости от количества сегментов.
     * Calculates price of the route depending on segment's quantity.
     *
     * @param totalSegments segment's quantity.
     * @return price of the route.
     */
    private static int calculateCost(int totalSegments) {
        int cost = 0;
        while (totalSegments > 0) {
            if (totalSegments >= 3) {
                cost += 10;
                totalSegments -= 3;
            } else if (totalSegments == 2) {
                cost += 7;
                totalSegments -= 2;
            } else if (totalSegments == 1) {
                cost += 5;
                totalSegments -= 1;
            }
        }
        return cost;
    }

    /**
     * Находит оптимальный маршрут между двумя городами.
     * Finds the most optimal route between 2 cities.
     *
     * @param startName departure city name
     * @param endName   arrival city name
     * @return Array which contains price of the route and the quantity of the segments.
     *         If route is not found, or there is no city with such name, returns {-1, -1}.
     */
    public static int[] findOptimalRoute(String startName, String endName) {
        City start = cities.get(startName);
        City end = cities.get(endName);

        if (start == null || end == null) {
            return new int[]{-1, -1}; // If either city is not found
        }

        Map<City, Integer> distances = new HashMap<>();
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(route -> route.segments));
        Set<City> visited = new HashSet<>();

        distances.put(start, 0);
        priorityQueue.add(new Route(start, 0));

        while (!priorityQueue.isEmpty()) {
            Route currentRoute = priorityQueue.poll();
            City currentCity = currentRoute.city;
            int currentSegments = currentRoute.segments;

            if (visited.contains(currentCity)) continue;
            visited.add(currentCity);

            if (currentCity.equals(end)) {
                int cost = calculateCost(currentSegments);
                return new int[]{cost, currentSegments};
            }

            for (Neighbor neighbor : currentCity.neighbors) {
                int newSegments = currentSegments + neighbor.segments;
                if (newSegments < distances.getOrDefault(neighbor.city, Integer.MAX_VALUE)) {
                    distances.put(neighbor.city, newSegments);
                    priorityQueue.add(new Route(neighbor.city, newSegments));
                }
            }
        }
        return new int[]{-1, -1}; // If route is not found.
    }

    /**
     * Function is user to initialize data about cities and theirs connections between
     * each other.
     */
    private static void initializeCities() {
        City birmingham = new City("Birmingham");
        City coventry = new City("Coventry");
        City swindon = new City("Swindon");
        City bristol = new City("Bristol");
        City northampton = new City("Northampton");
        City reading = new City("Reading");
        City london = new City("London");

        birmingham.addNeighbor(coventry, 1);
        birmingham.addNeighbor(swindon, 4);
        birmingham.addNeighbor(bristol, 3);

        coventry.addNeighbor(birmingham, 1);
        coventry.addNeighbor(northampton, 2);

        bristol.addNeighbor(birmingham, 3);
        bristol.addNeighbor(swindon, 2);

        swindon.addNeighbor(birmingham, 4);
        swindon.addNeighbor(bristol, 2);
        swindon.addNeighbor(reading, 4);

        northampton.addNeighbor(coventry, 2);
        northampton.addNeighbor(london, 2);

        reading.addNeighbor(swindon, 4);
        reading.addNeighbor(london, 1);

        london.addNeighbor(northampton, 2);
        london.addNeighbor(reading, 1);

        cities.put(birmingham.name, birmingham);
        cities.put(coventry.name, coventry);
        cities.put(swindon.name, swindon);
        cities.put(bristol.name, bristol);
        cities.put(northampton.name, northampton);
        cities.put(reading.name, reading);
        cities.put(london.name, london);
    }

    static {
        initializeCities();
    }
}