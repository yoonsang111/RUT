package com.stream.tour.global.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class CollectorsUtil {

    public static <T, ID> Map<ID, T> toMap(List<T> list, Function<T, ID> idExtractor) {
        return list.stream().collect(Collectors.toMap(idExtractor, Function.identity()));
    }

    public static <T, ID> List<ID> getIdsFrom(List<T> list, Function<T, ID> idExtractor) {
        return list.stream().map(idExtractor).toList();
    }

    public static <T, ID> Map<ID, List<T>> groupBy(List<T> list, Function<T, ID> idExtractor) {
        return list.stream().collect(groupingBy(idExtractor));
    }

}
