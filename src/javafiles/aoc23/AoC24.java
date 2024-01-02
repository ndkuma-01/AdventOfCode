package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Frac;
import Utilities.aoc23.Pair;
import Utilities.aoc23.TripletBigInt;
import Utilities.aoc23.TripletLong;
import org.jgrapht.alg.util.Triple;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class AoC24 implements DAYID {

    public record posveloc(Long px, Long py, Long pz, Long vx, Long vy, Long vz){
        public posveloc(List<Long>a){
            this(a.get(0), a.get(1), a.get(2), a.get(3),a.get(4), a.get(5));
        }

        @Override
        public String toString() {
            return "( " + px + ", " + py + ", " + pz + ", " + vx + ", " + vy + ", " + vz + ")";
        }
    };

    public Pair<TripletLong, Long> getPlane(TripletLong p1, TripletLong v1, TripletLong p2, TripletLong v2){
        return new Pair<>(TripletLong.cross((TripletLong.sub(p1,p2)), (TripletLong.sub(v1,v2))), TripletLong.dot((TripletLong.sub(p1,p2)), TripletLong.cross(v1,v2)));
    }


    @Override
    public String p1() throws IOException {
        List<List<List<Long>>> in = combinations( new GetInputs(23,24)
                .filetoArrayList()
                .stream()
                .map(x-> List.of( Long.parseLong(x.split(" @ ")[0].split(", ")[0].trim()), Long.parseLong(x.split(" @ ")[0].split(", ")[1].trim()) , Long.parseLong(x.split(" @ ")[0].split(", ")[2].trim()) , Long.parseLong(x.split(" @ ")[1].split(", ")[0].trim()), Long.parseLong(x.split(" @ ")[1].split(", ")[1].trim()), Long.parseLong(x.split(" @ ")[1].split(", ")[2].trim())     )  ).toList(),2);

        int n = 0;
        for(int i = 0 ; i  <in.size(); i++){
            posveloc a = new posveloc(in.get(i).get(0)), b = new posveloc(in.get(i).get(1));
            long d = a.vx * b.vy - a.vy * b.vx;
            if(d==0){continue;}
            Frac t = new Frac(), u = new Frac();
            t.setNumerator(( b.px - a.px ) * b.vy - ( b.py - a.py ) * b.vx);
            t.setDenominator(d);
            u.setNumerator( ( b.px - a.px ) * a.vy - ( b.py - a.py ) * a.vx);
            u.setDenominator(d);
            n+= ( (t.getNumerator() / t.getDenominator() >= 0) && (u.getNumerator() / u.getDenominator() >= 0) && ((200000000000000L <= a.px + (t.getNumerator()/t.getDenominator()) * a.vx) && ( (400000000000000L >= a.px + (t.getNumerator()/t.getDenominator()) * a.vx))  ) &&  ((200000000000000L <= a.py + (t.getNumerator()/t.getDenominator()) * a.vy) && ( (400000000000000L >= a.py + (t.getNumerator()/t.getDenominator()) * a.vy)) ))? 1:0;

        }
        return String.valueOf(n);
    }
    List<List<TripletLong>> in = new ArrayList<>();

    @Override
    public String p2() throws IOException {
        new GetInputs(23,24)
                .filetoArrayList()
                .stream()
                .map(x-> List.of( Long.parseLong(x.split(" @ ")[0].split(", ")[0].trim()), Long.parseLong(x.split(" @ ")[0].split(", ")[1].trim()) , Long.parseLong(x.split(" @ ")[0].split(", ")[2].trim()) , Long.parseLong(x.split(" @ ")[1].split(", ")[0].trim()), Long.parseLong(x.split(" @ ")[1].split(", ")[1].trim()), Long.parseLong(x.split(" @ ")[1].split(", ")[2].trim())     )  ).toList().stream().forEach(y-> in.add(List.of(new TripletLong(y), new TripletLong(y.subList(3, y.size())))));
        TripletLong p1 = in.get(0).get(0), v1 = in.get(0).get(1);
        TripletLong p2  = new TripletLong(0L,0L,0L), v2  = new TripletLong(0L,0L,0L), p3 = new TripletLong(0L,0L,0L),  v3 = new TripletLong(0L,0L,0L);
        int i = 0;
        for( i = 1 ; i < in.size(); i++){
            if(TripletLong.indep(in.get(0).get(1), in.get(i).get(1))){
                p2 = in.get(i).get(0);
                v2 = in.get(i).get(1);
                break;
            }
        }
        for(int j = i+1 ; j<in.size(); j++ ){
            if(TripletLong.indep(v1, in.get(j).get(1)) && TripletLong.indep(v2,  in.get(j).get(1))){
                p3 = in.get(j).get(0);
                v3 = in.get(j).get(1);
                break;
            }
        }
        Pair<TripletBigInt, BigInteger> a = new Pair<>(new TripletBigInt(getPlane(p1, v1, p2, v2).a()), new BigInteger(String.valueOf(getPlane(p1, v1, p2, v2).b())));
        Pair<TripletBigInt, BigInteger> b = new Pair<>(new TripletBigInt(getPlane(p1, v1, p3, v3).a()), new BigInteger(String.valueOf(getPlane(p1, v1, p3, v3).b())));
        Pair<TripletBigInt, BigInteger> c = new Pair<>(new TripletBigInt(getPlane(p2, v2, p3, v3).a()), new BigInteger(String.valueOf(getPlane(p2, v2, p3, v3).b())));
        TripletBigInt w = TripletBigInt.lin(a.b(), TripletBigInt.cross(b.a(), c.a()), b.b(), TripletBigInt.cross(c.a(), a.a()), c.b(), TripletBigInt.cross(a.a(), b.a()));
        BigInteger t = TripletBigInt.dot(a.a(), TripletBigInt.cross(b.a(),c.a()));

        System.out.println("w: " + w);
        w = new TripletBigInt(w.getX().divide(t), w.getY().divide(t), w.getZ().divide(t));

        System.out.println("t: " + t);
        System.out.println(w);
        TripletBigInt rock = TripletBigInt.lin((TripletBigInt.dot(TripletBigInt.cross(TripletBigInt.sub(new TripletBigInt(v1), w), TripletBigInt.sub(new TripletBigInt(v2), w)), TripletBigInt.cross(new TripletBigInt(p2), TripletBigInt.sub(new TripletBigInt(v2), w)))), TripletBigInt.sub(new TripletBigInt(v1), w), new BigInteger("-1").multiply(TripletBigInt.dot(TripletBigInt.cross(TripletBigInt.sub(new TripletBigInt(v1), w), TripletBigInt.sub(new TripletBigInt(v2), w)), TripletBigInt.cross(new TripletBigInt(p1), TripletBigInt.sub(new TripletBigInt(v1), w)))), TripletBigInt.sub(new TripletBigInt(v2), w), TripletBigInt.dot(new TripletBigInt(p1), TripletBigInt.cross(TripletBigInt.sub(new TripletBigInt(v1), w), TripletBigInt.sub(new TripletBigInt(v2), w))), TripletBigInt.cross(TripletBigInt.sub(new TripletBigInt(v1), w), TripletBigInt.sub(new TripletBigInt(v2), w)));
        return String.valueOf( rock.sum().divide(TripletBigInt.dot(TripletBigInt.cross(TripletBigInt.sub(new TripletBigInt(v1), w), TripletBigInt.sub(new TripletBigInt(v2), w)), TripletBigInt.cross(TripletBigInt.sub(new TripletBigInt(v1), w), TripletBigInt.sub(new TripletBigInt(v2), w)))));
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC24());
    }



    public static List<List<List<Long>>> combinations(List<List<Long>> elements, int r) {
        if (r <= 0 || elements.isEmpty() || r > elements.size()) {
            throw new IllegalArgumentException("Invalid input");
        }

        List<List<List<Long>>> result = new ArrayList<>();
        List<List<Long>> combination = new ArrayList<>(Collections.nCopies(r, new ArrayList<>()));

        combineHelper(elements, 0, combination, 0, r, result);
        return result;
    }

    private static void combineHelper(List<List<Long>> elements, int start, List<List<Long>> combination, int index, int r, List<List<List<Long>>> result) {
        if (index == r) {
            result.add(new ArrayList<>(combination));
            return;
        }
        for (int i = start; i < elements.size(); i++) {
            combination.set(index, elements.get(i));
            combineHelper(elements, i + 1, combination, index + 1, r, result);
        }
    }

}


