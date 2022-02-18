package ch7.matrix;

public class Matrix {

    public static void f(double[][] a) {
        int i = 0, j = 0;
        while (i < a.length) {
            if (a[i].length != a.length) {
                throw new IllegalArgumentException();
            }
            i++;
        }
        i = 0;
        while (i < a.length) {
            j = 0;
            while (j < 1) {
                double temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
                j++;
            }
        }
        i++
    }
}
