package de.oskar;

import lombok.Getter;

import java.util.Map;

@Getter
public class Graph {

    private final Node[] nodes;

    Map<Node, Map<Node, Integer>> graph;

    public Graph(Node[] nodes) {
        this.nodes = nodes;
    }
}
