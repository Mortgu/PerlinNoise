package de.ciel.modules;

import lombok.Getter;

public class RiverGenerator {

    @Getter
    private final double[][] map;

    public RiverGenerator(double[][] map) {
        this.map = map;
    }
}
