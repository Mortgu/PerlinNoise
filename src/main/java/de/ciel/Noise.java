package de.ciel;

import de.ciel.utils.Permutation;
import lombok.Getter;
import org.example.PerlinNoise;

@Getter
public class Noise {

    private final int[] permutation;

    private final int width, height;
    private final double density, threshold;
    private final int octaves;
    private final double persistence, baseFrequency;

    public Noise(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.density = 0.5;
        this.threshold = 0.5;
        this.octaves = 6;
        this.persistence = 0.45;
        this.baseFrequency = 9.0;
        this.permutation = new int[512];
        int[] perm = Permutation.generate(seed);

        for (int i = 0; i < 256; i++) {
            this.permutation[256 + i] = permutation[i] = perm[i];
        }
    }

    public Noise(int width, int height, int[] permutation) {
        this.width = width;
        this.height = height;
        this.density = 0.5;
        this.threshold = 0.5;
        this.octaves = 6;
        this.persistence = 0.45;
        this.baseFrequency = 9.0;
        this.permutation = permutation;
    }

    public double[][] generate() {
        double[][] map = new double[height][width];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                double noise = brownianMolecularMotion(row, column, width, height,
                        density, baseFrequency, octaves, persistence);
                noise = (noise + 1) / 2;

                map[row][column] = noise;
            }
        }

        return map;
    }

    private double brownianMolecularMotion(int row, int column, int width, int height, double density, double baseFrequency, int octaves, double persistence) {
        double total = 0;
        double frequency = baseFrequency;
        double amplitude = 1;
        double maxValue = 0;

        for (int i = 0; i < octaves; i++) {
            double nx = ((double) column / width * frequency) * density;
            double ny = ((double) row / height * frequency) * density;

            total += noise(nx, ny, 1) * amplitude;

            maxValue += amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }

        return total / maxValue;
    }

    private double noise(double x, double y, double z) {
        int X = (int) Math.floor(x) & 255, Y = (int) Math.floor(y) & 255, Z = (int) Math.floor(z) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        double u = fade(x), v = fade(y), w = fade(z);

        int A = permutation[X] + Y, AA = permutation[A] + Z, AB = permutation[A + 1] + Z,
                B = permutation[X + 1] + Y, BA = permutation[B] + Z, BB = permutation[B + 1] + Z;

        return lerp(w, lerp(v, lerp(u, grad(permutation[AA], x, y, z),
                                grad(permutation[BA], x - 1, y, z)),
                        lerp(u, grad(permutation[AB], x, y - 1, z),
                                grad(permutation[BB], x - 1, y - 1, z))),
                lerp(v, lerp(u, grad(permutation[AA + 1], x, y, z - 1),
                                grad(permutation[BA + 1], x - 1, y, z - 1)),
                        lerp(u, grad(permutation[AB + 1], x, y - 1, z - 1),
                                grad(permutation[BB + 1], x - 1, y - 1, z - 1))));
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private double grad(int hash, double x, double y, double z) {
        int h = hash & 15; 
        double u = h < 8 ? x : y, v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

}
