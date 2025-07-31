package de.ciel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static int width = 4960, height = 3508;

    public static void main(String[] args) throws IOException {
        SecureRandom secureRandom = new SecureRandom();
        long seed = secureRandom.nextLong();

        Noise noise = new Noise(width, height, seed);
        double[][] map = noise.generate();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                double value = map[row][column];

                Color pixelColor;
                if (value > 0.5) {
                    // Landmassen: Grün mit Abstufungen
                    float brightnessFactor = (float) (value - 0.5) * 2; // Skaliert von 0 bis 1
                    pixelColor = interpolateColor(new Color(38, 194, 38), new Color(11, 80, 11), brightnessFactor);
                } else {
                    // Wasser: Blau mit Abstufungen
                    float brightnessFactor = (float) value * 2; // Skaliert von 0 bis 1
                    pixelColor = interpolateColor(new Color(25, 25, 183), new Color(17, 125, 204), brightnessFactor);
                }

                bufferedImage.setRGB(column, row, pixelColor.getRGB());
            }
        }

        File file = new File("test.png");
        try {
            ImageIO.write(bufferedImage, "png", file);
            System.out.println("Datei gespeichert: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color interpolateColor(Color color1, Color color2, double t) {
        int red = (int) (color1.getRed() * (1 - t) + color2.getRed() * t);
        int green = (int) (color1.getGreen() * (1 - t) + color2.getGreen() * t);
        int blue = (int) (color1.getBlue() * (1 - t) + color2.getBlue() * t);
        return new Color(red, green, blue);
    }
}
