package de.oskar;

import lombok.Getter;

@Getter
public class Matrix {

    private final int rows, columns;
    private final double[][] data;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        this.data = new double[rows][columns];
    }

    public Matrix(double[][] data) {
        this.data = data;

        rows = data.length;
        columns = data[0].length;
    }

    public Matrix(int[][] data) {
        this.data = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = new double[data[i].length];
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] = data[i][j];
            }
        }

        rows = data.length;
        columns = data[0].length;
    }

    private Matrix(Matrix matrix) {
        this(matrix.data);
    }

    public static Matrix random(int rows, int columns) {
        Matrix matrix = new Matrix(rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.data[i][j] = Math.random();
            }
        }

        return matrix;
    }

    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }

    private void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public Matrix transpose() {
        Matrix A = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }

    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.rows != A.rows || B.columns != A.columns) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.columns != B.rows) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.rows, B.columns);
        for (int i = 0; i < C.rows; i++)
            for (int j = 0; j < C.columns; j++)
                for (int k = 0; k < A.columns; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }

    public void show() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                System.out.print(String.valueOf(data[i][j]) + " ");
            System.out.println();
        }
    }

    public double[] getColumn(int columns) {
        return this.data[columns];
    }
}
