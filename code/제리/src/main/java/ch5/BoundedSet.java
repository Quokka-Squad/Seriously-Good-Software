package ch5;

import java.util.Deque;
import java.util.LinkedList;

public class BoundedSet<T> {

	private int maxSize;
	private Deque<T> deque;

	public BoundedSet(int maxSize) {
		this.maxSize = maxSize;
		this.deque = new LinkedList<>();
	}

	public void add(T elem) {
		if (deque.size() == maxSize) {
			if (deque.contains(elem)) {
				deque.remove(elem);
				deque.addLast(elem);
			} else {
				deque.poll();
				deque.addLast(elem);
			}
		} else {
			deque.addLast(elem);
		}
	}

	public boolean contains(T elem) {
		return deque.contains(elem);
	}

}
