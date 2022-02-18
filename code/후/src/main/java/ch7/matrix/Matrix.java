package ch7.matrix;

public class Matrix {

    public static void f(double[][] a) {
        if (!isSquare(a)) {
            throw new IllegalArgumentException("주어진 행렬이 정사각형이 아닙니다.");
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

    public static boolean isSquare(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != a.length) {
                return false;
            }
        }
        return true;
    }
}
