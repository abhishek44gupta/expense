package com.number;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EvenNumber {

    public static List<Integer> filterEvenNumbers(List<Integer> allNumbers) {
        if (allNumbers == null) {
            return Collections.emptyList();
        }
        return allNumbers.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
    }
}
