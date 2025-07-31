package de.ciel.utils;

import java.util.Random;

public class Permutation {

    public static int[] generate(long seed) {
        int[] permutation = new int[256];
        Random random = new Random(seed);

        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[index];
            permutation[index] = temp;
        }

        int[] perm = new int[512];
        for (int i = 0; i < 512; i++) {
            perm[i] = permutation[i % 256];
        }

        return perm;
    }

}
