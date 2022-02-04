package ch5.boundedset;

public class BoundedSet<T> {

    private final int MAX_SIZE;

    public BoundedSet(int maxSize) {
        this.MAX_SIZE = maxSize;
    }

    public void add(T elem) {
        // TODO
        // 1. 사전 조건 : 인자는 null이 아님
        // 2. 패널티 : NPE
        // 3. 사후 조건 : 주어진 요소를 크기가 정해진 집합에 추가
        // 요소의 개수가 용량을 초과하면 가장 오래된 요소 제거
        // 이미 존재하는 요소를 다시 추가하면 해당 요소를 갱신
    }

    public boolean contains(T elem) {
        // TODO
        // 1. 사전 조건 : 인자는 null이 아님
        // 2. 패널티 : NPE
        // 3. 사후 조건 : 인자로 주어진 요소가 집합에 이미 존재하면 true 리턴
        // 이 메서드는 집합을 변경하지 않는다.
        return false;
    }
}
