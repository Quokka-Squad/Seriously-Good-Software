package ch4.multiset;

public interface MultiSet<T> {

    public void add(T elem);

    public long count(T elem);
}
