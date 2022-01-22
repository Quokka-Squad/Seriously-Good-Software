package ch3.빠른삽입;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntStats {

	// insert(), getAverage() -> O(1)
	private final List<Integer> list;
	private double average;

	public IntStats() {
		list = new ArrayList<>();
		average = 0;
	}

	public void insert(int n) {
		average = (average * list.size() + n) / (list.size() + 1);
		list.add(n);
	}

	public double getAverage() {
		return average;
	}

	public double getMedian() {
		if (list.isEmpty()) {
			return 0;
		}
		Collections.sort(list);
		if (list.size() % 2 == 1) {
			return list.get(list.size() / 2);
		}
		return ((double) list.get(list.size() / 2) + list.get(list.size() / 2 - 1)) / 2;
	}

}
