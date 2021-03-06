package code.민지노.ch5.contracts;

import java.util.*;

public class BoundedSet<T> {
    private final LinkedList<T> data;
    private final int capacity;
    
    public BoundedSet(int capacity) {
        data = new LinkedList<>();
        this.capacity = capacity;
    }
    
    public BoundedSet(BoundedSet<T> other) {
        data = new LinkedList<>(other.data);
        capacity = other.capacity;
    }
    
    public void add(T elem) {
        if (elem==null)
            throw new NullPointerException();
    
        BoundedSet<T> copy = null;
        assert (copy = new BoundedSet<>(this)) != null;
        
        data.remove(elem);
        if (data.size() == capacity) {
            data.removeFirst();
        }
        data.addLast(elem);
        
        assert postAdd(copy, elem) : "add failed its post-condition!";
    }
    
    private boolean postAdd(BoundedSet<T> oldSet, T newElement) {
        // newElement must be the newest
        if (!data.getLast().equals(newElement))
            return false;
        List<T> copyOfCurrent = new ArrayList<>(data);
        copyOfCurrent.remove(newElement);
        oldSet.data.remove(newElement);        
        if (oldSet.data.size()==capacity) {
            oldSet.data.removeFirst();
        }
        return oldSet.data.equals(copyOfCurrent);
    }
    
    public boolean contains(T elem) {
        return data.contains(elem);
    }
}
