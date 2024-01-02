package Utilities.General;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {


    public static <T> Stream<T> reverseStream(Stream<T> input) {
        Deque<T> deque = input.collect(Collectors.toCollection(ArrayDeque::new));
        Iterable<T> iterable = deque::descendingIterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
