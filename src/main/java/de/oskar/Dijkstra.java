package de.oskar;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import de.oskar.Node;

public class Dijkstra {

    public static void main(String[] args) {
        Matrix adjacencyMatrix = new Matrix(new int[][]{
                {0, 1, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 1, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
        });
        ArrayList<Node> nodes = fromAdjacencyToNodes(adjacencyMatrix);
        Map<Node, Map<Node, Integer>> graph = fromNodesToGraph(nodes, adjacencyMatrix);
        dijkstra(graph, nodes.getFirst());

        for (Node node : graph.keySet()) {
            System.out.print("(" + node.getName() + ") -> ");
            for (Map.Entry<Node, Integer> entries : graph.get(node).entrySet()) {
                System.out.print(entries.getKey().getName() + ", ");
            }
            System.out.println();
        }
    }

    public static void dijkstra(Map<Node, Map<Node, Integer>> graph, Node start) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        start.setDistance(0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();

            for (Map.Entry<Node, Integer> neighborEntry : graph.get(current).entrySet()) {
                Node neighborNode = neighborEntry.getKey();
                int newDistance = current.getDistance() + neighborEntry.getValue();

                if (newDistance < neighborNode.getDistance()) {
                    priorityQueue.remove(neighborNode);
                    neighborNode.setDistance(newDistance);
                    priorityQueue.add(neighborNode);
                }
            }
        }
    }

    public static Map<Node, Map<Node, Integer>> fromNodesToGraph(ArrayList<Node> nodes, Matrix adjacency) {
        Map<Node, Map<Node, Integer>> graph = new HashMap<>();

        for (int i = 0; i < adjacency.getColumns(); i++) {
            Map<Node, Integer> neighbors = new HashMap<>();

            for (int k = 0; k < adjacency.getColumn(i).length; k++) {
                if (adjacency.getColumn(i)[k] != 1.0) continue;
                Random r = new Random();
                neighbors.put(nodes.get(k), r.nextInt(100 - 10) + 10);
            }

            graph.put(nodes.get(i), neighbors);
        }

        return graph;
    }

    public static ArrayList<Node> fromAdjacencyToNodes(Matrix adjacency) {
        ArrayList<Node> nodes = new ArrayList<>();

        for (int i = 0; i < adjacency.getColumns(); i++) {
            Node node = new Node(String.valueOf(i));
            nodes.add(node);
        }

        return nodes;
    }

    public static Matrix getDistanceMatrix(Map<Node, Map<Node, Integer>> graph) {
        return new Matrix(graph.size(), graph.size());
    }
}
