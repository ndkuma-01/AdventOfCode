package Utilities.aoc23;

import java.util.List;
import java.util.stream.Stream;

public class TripletLong {
    private Long x;
    private Long y;
    private Long z;

    public TripletLong(Long x, Long y, Long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public TripletLong(List<Long> a) throws ArrayIndexOutOfBoundsException{
        this(a.get(0), a.get(1), a.get(2));
    }
    public Stream<Long> stream(){
        return Stream.of(x,y,z);
    }
    public Long sum(){
        return x+y+z;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public static TripletLong cross(TripletLong a, TripletLong b) {
       return new TripletLong(a.y*b.z - a.z*b.y, a.z*b.x - a.x*b.z, a.x*b.y - a.y*b.x);
    }
    public static TripletLong sub(TripletLong a, TripletLong b){
        return new TripletLong(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static long dot(TripletLong a, TripletLong b){
        return a.x*b.x + a.y*b.y + a.z*b.z;
    }
    public static TripletLong lin(Long r, TripletLong a, Long s, TripletLong b, Long t, TripletLong c){
        return new TripletLong(r * a.x + s * b.x + t * c.x, r * a.y + s * b.y + t * c.y, r * a.z + s * b.z + t * c.z);
    }

    public static boolean indep(TripletLong a, TripletLong b){
        return (cross(a,b).stream().filter(x-> x!=0).toList().size() >0);
    }

}
