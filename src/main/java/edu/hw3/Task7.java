package edu.hw3;

import java.util.Comparator;

public class Task7 {
    static class NullComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return 0;
            } else if (s1 == null) {
                return -1;
            } else if (s2 == null) {
                return 1;
            }
            return s1.compareTo(s2);
        }
    }

}
