package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        int width = 500, height = 500;
        double density = 5.0;

        Window window = new Window(width, height);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double nx = ((double) x / width) * density, ny = ((double) y / height) * density;
                double noise = (PerlinNoise.noise(nx, ny, 1) + 1) / 2;

                int alpha = (int) (noise * 255);
                int rgb = (alpha << 24) | (0 << 16) | (0 << 8);
                bufferedImage.setRGB(x, y, rgb);
            }
        }


        g.dispose();

        window.paintImage(bufferedImage);
        window.showWindow();
    }
}