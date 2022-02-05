package ch5.practice;

import java.util.ArrayList;
import java.util.List;

public class InterleaveLists {

    public static List interleaveLists(List a, List b) {
        if (a == null || b == null) {
            throw new NullPointerException();
        }
        if (a.size() != b.size()) {
            throw new IllegalArgumentException();
        }
        List interleave = new ArrayList();
        for (int i = 0; i < a.size(); i++) {
            interleave.add(a.get(i));
            interleave.add(b.get(i));
        }
        return interleave;
    }
}
