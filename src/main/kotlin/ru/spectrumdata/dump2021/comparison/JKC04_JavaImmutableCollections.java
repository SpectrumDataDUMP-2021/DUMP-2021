package ru.spectrumdata.dump2021.comparison;

import java.util.List;

public class JKC04_JavaImmutableCollections {
    // Мы можем добавить в коллекцию null значение
    private final List<String> myList = List.of("a", "b", null);

    // В интерфейсе коллекции есть метод `add`
    public void add(String s) {
        myList.add(s);
    }
}
