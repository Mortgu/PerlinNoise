package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        int width = 1000, height = 1000;
        double density = 0.3;
        double threshold = 0.5; // Threshold for land vs water
        int octaves = 6;
        double persistence = 0.5;
        double baseFrequency = 10.0;

        Window window = new Window(width, height);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double noise = fBm(x, y, width, height, density, baseFrequency, octaves, persistence);
                noise = (noise + 1) / 2;

                Color color;
                if (noise > threshold) {
                    color = interpolateColor(new Color(34, 139, 34), new Color(102, 145, 102), noise);
                }else {
                    color = interpolateColor(new Color(0, 0, 139), new Color(173, 216, 230), noise);
                }

                //int alpha = (int) (noise * 255);
                //int rgb = (alpha << 24) | (0 << 16) | (0 << 8);
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }


        g.dispose();

        window.paintImage(bufferedImage);
        window.showWindow();
    }

    public static double fBm(int x, int y, int width, int height, double density, double baseFrequency, int octaves, double persistence) {
        double total = 0;
        double frequency = baseFrequency;
        double amplitude = 1;
        double maxValue = 0;

        for (int i = 0; i < octaves; i++) {
            double nx = ((double) x / width * frequency) * density;
            double ny = ((double) y / height * frequency) * density;

            total += PerlinNoise.noise(nx, ny, 1) * amplitude;

            maxValue += amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }

        return total / maxValue;
    }

    public static Color interpolateColor(Color color1, Color color2, double t) {
        int red = (int) (color1.getRed() * (1 - t) + color2.getRed() * t);
        int green = (int) (color1.getGreen() * (1 - t) + color2.getGreen() * t);
        int blue = (int) (color1.getBlue() * (1 - t) + color2.getBlue() * t);
        return new Color(red, green, blue);
    }
}