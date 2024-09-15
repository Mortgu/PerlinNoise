package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Main {
    static final int width = 1000, height = 1000;
    static boolean[][] visited = new boolean[width][height];
    static HashMap<Integer, ArrayList<int[]>> landmasses = new HashMap<>();
    static int countryCount = 0;
    private static BufferedImage bufferedImage;

    public static void main(String[] args) {
        double density = 0.6;
        double threshold = 0.5; // Threshold for land vs water
        int octaves = 6;
        double persistence = 0.45;
        double baseFrequency = 9.0;

        Window window = new Window(width, height);

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double noise = fBm(x, y, width, height, density, baseFrequency, octaves, persistence);
                noise = (noise + 1) / 2;

                Color color;
                if (noise > threshold) {
                    color = interpolateColor(new Color(34, 139, 34), new Color(102, 145, 102), noise);
                } else {
                    color = interpolateColor(new Color(0, 0, 139), new Color(173, 216, 230), noise);
                }

                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }

        identifyLandmasses(bufferedImage, width, height);
        visualizeLandmasses(bufferedImage);

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

    public static void identifyLandmasses(BufferedImage image, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!visited[x][y] && isLand(image.getRGB(x, y))) {
                    ArrayList<int[]> landmass = new ArrayList<>();
                    floodFill(image, x, y, landmass);
                    if (!landmass.isEmpty()) {
                        landmasses.put(countryCount++, landmass);
                    }
                }
            }
        }
        subdivideLandmasses();
    }

    public static void subdivideLandmasses() {
        HashMap<Integer, ArrayList<int[]>> newLandmasses = new HashMap<>();
        int newCountryCount = 0;

        for (ArrayList<int[]> landmass : landmasses.values()) {
            int numCountries = Math.max(1, landmass.size() / 10000); // Example: 1 country per 10,000 pixels
            for (int i = 0; i < numCountries; i++) {
                ArrayList<int[]> country = new ArrayList<>();
                int[] startPixel = landmass.get(i * (landmass.size() / numCountries));
                floodFillCountry(landmass, startPixel[0], startPixel[1], country);
                newLandmasses.put(newCountryCount++, country);
            }
        }

        landmasses = newLandmasses;
    }

    public static void floodFillCountry(ArrayList<int[]> landmass, int startX, int startY, ArrayList<int[]> country) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];

            if (x < 0 || x >= width || y < 0 || y >= height || visited[x][y] || !isLand(bufferedImage.getRGB(x, y))) {
                continue;
            }

            visited[x][y] = true;
            country.add(new int[]{x, y});
            landmass.remove(new int[]{x, y});

            stack.push(new int[]{x + 1, y});
            stack.push(new int[]{x - 1, y});
            stack.push(new int[]{x, y + 1});
            stack.push(new int[]{x, y - 1});
        }
    }

    public static void floodFill(BufferedImage image, int startX, int startY, ArrayList<int[]> landmass) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];

            if (x < 0 || x >= width || y < 0 || y >= height || visited[x][y] || !isLand(image.getRGB(x, y))) {
                continue;
            }

            visited[x][y] = true;
            landmass.add(new int[]{x, y});

            stack.push(new int[]{x + 1, y});
            stack.push(new int[]{x - 1, y});
            stack.push(new int[]{x, y + 1});
            stack.push(new int[]{x, y - 1});
        }
    }

    public static boolean isLand(int rgb) {
        Color color = new Color(rgb);
        return color.getGreen() > color.getBlue();
    }

    public static void visualizeLandmasses(BufferedImage image) {
        Random random = new Random();
        for (ArrayList<int[]> country : landmasses.values()) {
            Color countryColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            System.out.println(country);
            for (int[] pixel : country) {
                image.setRGB(pixel[0], pixel[1], countryColor.getRGB());
            }
        }
    }
}