package Utilities.aoc22;


import java.util.Objects;

public class Cyclones {

    public HyperDuplex pos,facing;

    public Cyclones(HyperDuplex pos, HyperDuplex facing) {
        this.pos = pos;
        this.facing = facing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cyclones cyclones = (Cyclones) o;
        return pos.equals(cyclones.pos) && facing.equals(cyclones.facing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, facing);
    }

    public String toString() {
        return pos.toString();
    }
}