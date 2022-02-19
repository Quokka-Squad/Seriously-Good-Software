package ch7.practice;

public class Practice {

    public static int countOccurrences(String s, char c) {
        return (int) s.chars()
            .filter(character -> character == c)
            .count();
    }
}
