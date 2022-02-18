package ch7.matrix;

public class Matrix {

    public static void f(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != a.length) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                double temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
                j++;
            }
        }
    }
}
