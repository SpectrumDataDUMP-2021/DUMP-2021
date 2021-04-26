package ru.spectrumdata.dump2021.comparison;

import java.util.List;

public class JKC04_JavaImmutableCollections {
    private final List<String> myList = List.of("a", "b", null); // спокойно добавил null !!!
    public void add(String s){
        myList.add(s); // есть такой метод в интерфейсе !!!
    }
}
