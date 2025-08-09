package de.ciel.modules;

import de.ciel.Noise;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

public class WorldGeneration {

    @Getter
    private final int width, height;

    @Getter
    private final long seed;

    @Getter
    private final double[][] map;

    public WorldGeneration() {
        this(4960, 3508);
    }

    public WorldGeneration(int width, int height) {
        this(width, height, new SecureRandom().nextLong());
    }

    public WorldGeneration(int width, int height, long seed) {
        this.width = width;
        this.height = height;

        this.seed = seed;

        Noise noise = new Noise(width, height, seed);
        this.map = noise.generate();
    }

    public void generateImage() {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                double value = this.map[row][column];

                Color pixelColor;
                if (value > 0.5) {
                    float brightnessFactor = (float) (value - 0.5) * 2; // Skaliert von 0 bis 1
                    pixelColor = interpolateColor(new Color(38, 194, 38), new Color(11, 80, 11), brightnessFactor);
                } else {
                    float brightnessFactor = (float) value * 2; // Skaliert von 0 bis 1
                    pixelColor = interpolateColor(new Color(25, 25, 183), new Color(17, 125, 204), brightnessFactor);
                }

                bufferedImage.setRGB(column, row, pixelColor.getRGB());
            }
        }

        save(bufferedImage, "world.png");
    }

    private void save(BufferedImage bufferedImage, String fileName) {
        File file = new File(fileName);
        try {
            ImageIO.write(bufferedImage, "png", file);
            System.out.println("[WorldGeneration] World generated. Image saved as " + fileName);
        } catch (IOException exception) {
            System.err.println("[WorldGeneration] Failed to save generated world!");
        }
    }

    private Color interpolateColor(Color color1, Color color2, double t) {
        int red = (int) (color1.getRed() * (1 - t) + color2.getRed() * t);
        int green = (int) (color1.getGreen() * (1 - t) + color2.getGreen() * t);
        int blue = (int) (color1.getBlue() * (1 - t) + color2.getBlue() * t);
        return new Color(red, green, blue);
    }
}
