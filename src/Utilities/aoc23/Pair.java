package Utilities.aoc23;

import java.util.Objects;
import java.util.function.BiFunction;

public record Pair<A, B>(A a, B b) implements Comparable<Pair<A, B>> {

    public static <A, B> Pair<A, B> pair(A a, B b) {
        return new Pair<>(a, b);
    }

    public A getLeft() {
        return a;
    }

    public B getRight() {
        return b;
    }



    public <C, D> Pair<C, D> map(BiFunction<A, B, Pair<C, D>> func) {
        return func.apply(a(), b());
    }

    @Override
    public int compareTo(Pair<A, B> t) {
        if (a instanceof Comparable && t.a instanceof Comparable) {
            return ((Comparable) a).compareTo(t.a);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "(" + a  + " " + b+ ")";
    }
}