package ch7.practice;

public class Practice {

    public static int f(String s, char c) {
        int i = 0, n = 0;
        boolean flag = true;
        while (flag) {
            if (s.charAt(i) == c) {
                n++;
            }
            if (i == s.length() - 1) {
                flag = false;
            } else {
                i++;
            }
        }
        return n;
    }
}
