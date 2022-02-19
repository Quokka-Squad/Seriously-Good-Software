package ch7.practice;

public class Practice {

    /**
     * 문자열 s에서 문자 c가 나타나는 수를 세어 반환한다.
     * @param s 문자열
     * @param c 몇 번 나타는지 세어볼 문자
     * @return 문자열 s에서 문자 c가 나타나는 수
     */
    public static int countOccurrences(String s, char c) {
        return (int) s.chars()
            .filter(character -> character == c)
            .count();
    }
}
