package Utilities.aoc23;

import java.util.Objects;
import java.util.stream.Stream;

public class UniversalAtomic <A>{
    private A atom;
    public UniversalAtomic(){
        this.atom = null;
    }

    public UniversalAtomic(A a){
        this.atom = a;
    }

    public A setAtom(A replace){
        A temp = atom;
        atom = replace;
        return temp;
    }
    public A getAtom(){
        return atom;
    }
    public Stream<A> stream(){
        return Stream.of(atom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversalAtomic<?> that = (UniversalAtomic<?>) o;
        return Objects.equals(atom, that.atom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atom);
    }
}
