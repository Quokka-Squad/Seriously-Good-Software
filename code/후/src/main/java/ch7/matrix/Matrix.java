package ch7.matrix;

public class Matrix {

    public static void transpose(double[][] matrix) {
        if (!isSquare(matrix)) {
            throw new IllegalArgumentException("주어진 행렬이 정사각형이 아닙니다.");
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public static boolean isSquare(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != matrix.length) {
                return false;
            }
        }
        return true;
    }
}
