package org.example;

import java.util.Random;

public final class PerlinNoise {

    private static final int[] p = new int[512];
    private static final int[] permutation = new int[256];

    static {
        generatePermutation();
        for (int i = 0; i < 256; i++) {
            p[256 + i] = p[i] = permutation[i];
        }
    }

    private static void generatePermutation() {
        Random random = new Random();

        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[index];
            permutation[index] = temp;
        }
    }

    public static double noise(double x, double y, double z) {
        int X = (int) Math.floor(x) & 255,
                Y = (int) Math.floor(y) & 255,
                Z = (int) Math.floor(z) & 255;
        x -= Math.floor(x);
        y -= Math.floor(y); // of point in cube
        z -= Math.floor(z);
        double u = fade(x), // Compute fade curves
                v = fade(y), // for each of x ,y , z
                w = fade(z);
        int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z, // Hash coordinates of
                B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z; // the 8 cube corners
// ... and add blended results from 8 corners of cube
        return lerp(w, lerp(v, lerp(u, grad(p[AA], x, y, z),
                                grad(p[BA], x - 1, y, z)),
                        lerp(u, grad(p[AB], x, y - 1, z),
                                grad(p[BB], x - 1, y - 1, z))),
                lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1),
                                grad(p[BA + 1], x - 1, y, z - 1)),
                        lerp(u, grad(p[AB + 1], x, y - 1, z - 1),
                                grad(p[BB + 1], x - 1, y - 1, z - 1))));
    }



    static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    static double grad(int hash, double x, double y, double z) {
        int h = hash & 15; // Convert low 4 bits of hash code
        double u = h < 8 ? x : y, // into 12 gradient directions
                v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}
