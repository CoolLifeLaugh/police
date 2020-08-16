package com.lhsj.police.core.lambda;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lhsj.police.core.collection.ReLists.emptyList;
import static java.util.function.Function.identity;

/**
 * stream util
 */
@SuppressWarnings("all")
public final class ReStreams {

    public static <T> Stream<T> ofNullable(Collection<T> collection) {
        return Optional.ofNullable(collection).orElse(emptyList()).stream();
    }

    public static <T> Stream<T> nonNull(Collection<T> collection) {
        return ofNullable(collection).filter(Objects::nonNull);
    }

    public static <K, V> Stream<Map.Entry<K, V>> ofNullable(Map<K, V> map) {
        return Optional.ofNullable(map).orElse(Maps.newHashMap()).entrySet().stream();
    }

    public static <K, V> Stream<Map.Entry<K, V>> nonNull(Map<K, V> map) {
        return ofNullable(map).filter(Objects::nonNull);
    }

    public static <T> BinaryOperator<T> leftOperator() {
        return (t1, t2) -> t1;
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toSafeMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, leftOperator());
    }

    public static <T, K> Collector<T, ?, Map<K, T>> toSafeKeyMap(
            Function<? super T, ? extends K> keyMapper) {
        return Collectors.toMap(keyMapper, identity(), leftOperator());
    }

}
