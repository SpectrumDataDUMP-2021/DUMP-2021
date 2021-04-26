package ru.spectrumdata.dump2021.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JKC08_JavaFullDemo {
    private String myValue;
    private final List<Integer> ints;

    JKC08_JavaFullDemo(String myValue, Iterable<Integer> ints) {
        setMyValue(myValue);
        ArrayList<Integer> internalInts = new ArrayList<>();

        if (null != ints) {
            for (Integer i : ints) {
                if (null == i) {
                    throw new NullPointerException();
                }
                internalInts.add(i);
            }
        }
        this.ints = List.of(internalInts.toArray(new Integer[]{}));
    }

    JKC08_JavaFullDemo() {
        this("", Collections.emptyList());
    }

    JKC08_JavaFullDemo(String myValue) {
        this(myValue, Collections.emptyList());
    }

    public String getMyValue() {
        if (null == myValue) {
            throw new NullPointerException();
        }

        return myValue;
    }

    public void setMyValue(String myValue) {
        if (null == myValue) {
            throw new NullPointerException();
        }

        this.myValue = myValue;
    }

    public List<Integer> getInts() {
        return ints;
    }
}
