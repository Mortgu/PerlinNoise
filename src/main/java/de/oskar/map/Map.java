package de.oskar.map;

import lombok.Getter;

@Getter
public class Map implements IMap {

    private final int width, height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void generate() {

    }
}
