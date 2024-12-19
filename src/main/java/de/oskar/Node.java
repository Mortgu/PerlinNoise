package de.oskar;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Node {

    private final String name;

    @Setter
    private int distance = Integer.MAX_VALUE;

    public Node(String name) {
        this.name = name;
    }
}
