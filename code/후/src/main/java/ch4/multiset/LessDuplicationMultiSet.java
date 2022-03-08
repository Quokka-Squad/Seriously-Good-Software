package ch4.multiset;

import java.util.*;

public class LessDuplicationMultiSet<T> implements MultiSet<T> {

    private List<T> data = new ArrayList<>();

    @Override
    public void add(T elem) {
        data.add(elem);
    }

    @Override
    public long count(T elem) {
        return data.stream().filter(x -> x.equals(elem)).count();
    }
}
