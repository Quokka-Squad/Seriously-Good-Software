package ch4.multiset;

import java.util.*;

public class ManyDuplicationMultiSet<T> implements MultiSet<T> {

    private List<T> elements = new ArrayList<>();
    private List<Long> repetitions = new ArrayList<>();

    @Override
    public void add(T elem) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(elem)) {
                repetitions.set(i, repetitions.get(i) + 1);
                return;
            }
        }
        elements.add(elem);
        repetitions.add(1L);
    }

    @Override
    public long count(T elem) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(elem)) {
                return repetitions.get(i);
            }
        }
        return 0;
    }
}
