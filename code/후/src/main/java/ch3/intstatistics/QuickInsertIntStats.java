package ch3.intstatistics;

import java.util.*;

public class QuickInsertIntStats implements IntStats {

    private List<Integer> numbers = new ArrayList<>();
    private int total = 0;

    @Override
    public void insert(int n) {
        numbers.add(n);
        total += n;
    }

    @Override
    public double getAverage() {
        return (double) total / numbers.size();
    }

    @Override
    public double getMedian() {
        Collections.sort(numbers);
        if (numbers.size() % 2 == 0) {
            return (numbers.get(numbers.size() / 2 - 1) + numbers.get(numbers.size() / 2)) / 2.0;
        }
        return numbers.get(numbers.size() / 2);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int getTotal() {
        return total;
    }
}
