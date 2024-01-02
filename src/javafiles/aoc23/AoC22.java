package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;
import Utilities.aoc23.UniversalAtomic;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AoC22 implements DAYID {
    public int fallCnt = 0;
    public record box(int x, int y, int z, int x0, int y0, int z0) implements Comparable{

        @Override
        public int compareTo(Object o) {
            box obj = (box) o;
            return (this.z < obj.z)? -1 : ((this.z == obj.z)? 0:1);
        }
        @Override
        public String toString() {
            return "[" + x + ", " + y + ", " + z  + ", " + x0 + ", " + y0 + ", "+ z0 + "]";
        }
    }

    @Override
    public String p1() throws IOException {
        List<box> boxes = new GetInputs(23,22).filetoArrayList().stream().map(x-> x.split("~")).map(y->  new box(Integer.parseInt(y[0].split(",")[0].trim()), Integer.parseInt(y[0].split(",")[1].trim()), Integer.parseInt(y[0].split(",")[2].trim()) , Integer.parseInt(y[1].split(",")[0].trim()), Integer.parseInt(y[1].split(",")[1].trim()) , Integer.parseInt(y[1].split(",")[2].trim()))).collect(Collectors.toList());
//        System.out.println(boxes);
        Collections.sort(boxes);
//        System.out.println(boxes);
        List<HashSet<List<Integer>>> bricks = new ArrayList<>();
        for(int i = 0 ; i < boxes.size(); i++){
            box curr = boxes.get(i);
            List<List<Integer>> correct = new ArrayList<>();
            for(int j = curr.x; j <= curr.x0; j++){
                correct.add(List.of(j, curr.y, curr.z));
            }
            for(int j = curr.y; j <= curr.y0; j++){
                correct.add(List.of(curr.x, j, curr.z));
            }
            for(int j = curr.z; j <= curr.z0; j++){
                correct.add(List.of(curr.x,curr.y, j));
            }
            bricks.add(new HashSet<>(correct));
        }
        int safe = 0;
        List<HashSet<List<Integer>>> fall = simulate(bricks, new HashSet<>(), false);
        System.out.println("fall: " + fall.size());
        for(HashSet<List<Integer>> b : fall){
            List<HashSet<List<Integer>>> temp2 = new ArrayList<>(fall);
//            System.out.println(safeTest(temp2, b));
            safe += (safeTest(temp2, b))? 1:0;
//            System.out.println("fall length" + fall.size());
        }

        return String.valueOf(safe);

    }

    private boolean safeTest(List<HashSet<List<Integer>>> fall, HashSet<List<Integer>> b) {
        HashSet<List<Integer>> floor = new HashSet<>();
        int i = fall.indexOf(b);
        for(int j = 0 ; j < i ; j++){
            HashSet<List<Integer>> brick = fall.get(j);
            for(List<Integer> x : brick){
                if(!b.contains(x)){
                    floor.add(x);
                }
            }
        }
        System.out.println("floor: " + floor.size());
        List<HashSet<List<Integer>>> afterI = fall.subList(i + 1, fall.size());
        List<HashSet<List<Integer>>> cloneAfterI = new ArrayList<>(afterI);

        var res = simulate(cloneAfterI, floor, true);
        System.out.println("res" +  res.size());
        System.out.println("xs " + afterI.size());
        return res.equals(afterI);

    }

    private List<HashSet<List<Integer>>> simulate(List<HashSet<List<Integer>>> bricks, HashSet<List<Integer>> floor, boolean cnt) {
        List<HashSet<List<Integer>>> fallen = new ArrayList<>();
        while(bricks.size() != 0){
            HashSet<List<Integer>> b = bricks.remove(0);
            HashSet<List<Integer>> start = new HashSet<>(b);
            UniversalAtomic<HashSet<List<Integer>>> temp1 = new UniversalAtomic<>(floor);
            while((b.stream().filter(z -> z.get(2) != 1).toList().size() == b.size() )&& b.stream().filter(z-> !temp1.getAtom().contains(List.of(z.get(0), z.get(1), z.get(2) - 1))).toList().size() == b.size()){
                HashSet<List<Integer>> temp = new HashSet<>();
                for(List<Integer> nums : b){
                    temp.add(List.of(nums.get(0), nums.get(1), nums.get(2) - 1));
                }
                b = temp;
            }
            if(cnt){
                fallCnt += (!b.equals(start))? 1 : 0;
            }
            fallen.add(b);
            floor = set_merge(floor, b);

        }
        return fallen;
    }


    public static <T> HashSet<T> set_merge(HashSet<T> set_1, HashSet<T> set_2){
        HashSet<T> my_set = (HashSet<T>) set_1.stream().collect(Collectors.toSet());
        my_set.addAll(set_2);
        return my_set;
    }
    @Override
    public String p2() throws IOException {
        return String.valueOf(fallCnt);
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC22());
    }
}
