package com.gvstave.mistergift.data.utils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {

    /**
     * Converts Iterable to stream
     */
    public static <T> Stream<T> of(final Iterable<T> iterable) {
        return toStream(iterable, false);
    }

    /**
     * Converts Iterable to parallel stream
     */
    public static <T> Stream<T> parallelStreamOf(final Iterable<T> iterable) {
        return toStream(iterable, true);
    }

	/**
     *
     * @param iterable
     * @param isParallel
     * @param <T>
     * @return
     */
    private static <T> Stream<T> toStream(final Iterable<T> iterable, final boolean isParallel) {
        return StreamSupport.stream(iterable.spliterator(), isParallel);
    }

}