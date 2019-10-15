package ru.krogot88.demorest.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: Сашок  Date: 15.10.2019 Time: 15:32
 */
public final class Utils {
    private Utils() {
    }

    public static List<Integer> createConsecutiveList(int size) {
        return IntStream.rangeClosed(1,size).boxed().collect(Collectors.toList());
    }
}
