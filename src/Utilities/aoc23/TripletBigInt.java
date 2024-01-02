package Utilities.aoc23;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

public class TripletBigInt {
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public TripletBigInt(BigInteger x, BigInteger y, BigInteger z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public TripletBigInt(TripletLong a) {
        this(new BigInteger(String.valueOf(a.getX())), new BigInteger(String.valueOf(a.getY())), new BigInteger(String.valueOf(a.getZ())));
    }
    public TripletBigInt(List<BigInteger> a) throws ArrayIndexOutOfBoundsException{
        this(a.get(0), a.get(1), a.get(2));
    }
    public Stream<BigInteger> stream(){
        return Stream.of(x,y,z);
    }
    public BigInteger sum(){
        return x.add(y).add(z);
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public BigInteger getZ() {
        return z;
    }

    public void setZ(BigInteger z) {
        this.z = z;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
    public static TripletBigInt cross(TripletBigInt a, TripletBigInt b) {
        return new TripletBigInt(
                a.y.multiply(b.z).subtract(a.z.multiply(b.y)),
                a.z.multiply(b.x).subtract(a.x.multiply(b.z)),
                a.x.multiply(b.y).subtract(a.y.multiply(b.x))
        );
    }
    public static TripletBigInt sub(TripletBigInt a, TripletBigInt b){
        return new TripletBigInt( a.x.subtract(b.x), a.y.subtract(b.y), a.z.subtract(b.z));
    }
    public static BigInteger dot(TripletBigInt a, TripletBigInt b){
        return a.x.multiply(b.x).add( a.y.multiply(b.y)).add( a.z.multiply(b.z));
    }
    public static TripletBigInt lin(BigInteger r, TripletBigInt a, BigInteger s, TripletBigInt b, BigInteger t, TripletBigInt c){
        return new TripletBigInt(
                r.multiply(a.x).add(s.multiply(b.x)).add(t.multiply(c.x)),
                r.multiply(a.y).add(s.multiply(b.y)).add(t.multiply(c.y)),
                r.multiply(a.z).add(s.multiply(b.z)).add(t.multiply(c.z))
        );
    }

    public static boolean indep(TripletBigInt a, TripletBigInt b){
        return (cross(a,b).stream().filter(x-> x.equals(new BigInteger("0"))).toList().size() >0);
    }

}
