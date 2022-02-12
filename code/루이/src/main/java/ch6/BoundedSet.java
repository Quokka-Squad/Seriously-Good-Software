package ch6;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class BoundedSet<T> {
    private LinkedList<T> data = new LinkedList<>();
    private final int capacity;

    public BoundedSet(int capacity) {
        this.capacity = capacity;
    }

    public BoundedSet(BoundedSet<T> other) {
        data = new LinkedList<>(other.data);
        this.capacity = other.capacity;
    }

    public void add(T element) {
        Objects.requireNonNull(element);
        data.remove(element);
        if (data.size() == capacity) {
            data.removeFirst();
        }
        data.add(element);
    }

    public boolean contains(T element) {
        return data.contains(element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundedSet<?> that = (BoundedSet<?>) o;
        return capacity == that.capacity && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, capacity);
    }

    public static void main(String[] args) {
        BoundedSet<Integer> set = new BoundedSet<Integer>(10);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(5);
        set.add(2);
        set.add(1);

        set.data.forEach(System.out::println);
    }
}
