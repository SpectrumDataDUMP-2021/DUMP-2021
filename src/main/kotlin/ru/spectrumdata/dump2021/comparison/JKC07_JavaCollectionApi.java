package ru.spectrumdata.dump2021.comparison;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JKC07_JavaCollectionApi {
    List<String> myList = List.of("a", "1", "2", "3", "b", "3", "cc");
    List<Integer> ints = myList.stream().map(x -> {
        try {
            return Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return null;
        }
    }).filter(Objects::nonNull).collect(Collectors.toList());
    Integer sumDistinct = ints.stream().distinct().reduce(Integer::sum).get();
}
