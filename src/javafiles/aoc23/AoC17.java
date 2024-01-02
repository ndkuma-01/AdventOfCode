package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class AoC17 implements DAYID {



    public record unit(int c, int x, int y, int d){
        public String toString(){
            return this.c + ", " + this.x + ", " + this.y + ", " + this.d;
        }
        @Override
        public boolean equals(Object obj) {
            unit o = (unit) obj;
            return ((x == o.x) && (y == o.y) && (d == o.d));
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y, d);
        }
    }
    public record reducedU(int x, int y, int d){
        public String toString(){
            return   this.x + ", " + this.y + ", " + this.d;
        }
        @Override
        public boolean equals(Object obj) {
            reducedU o = (reducedU) obj;
            return ((x == o.x) && (y == o.y) && (d == o.d));
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y, d);
        }

    }

    public long djistrka(int min, int max){
        PriorityQueue<unit> q = new PriorityQueue<>(Comparator.comparingInt(unit::c));
        q.add(new unit(0,0,0,-1));
        HashSet<reducedU> visited = new HashSet<>();
        HashMap<reducedU, Integer> cMap = new HashMap<>();
        while(!q.isEmpty()){
//            System.out.println(q.size());
            unit curr = q.poll();
            if(curr.x == g.size() - 1 && curr.y == g.get(0).size() - 1 ){
                return curr.c;
            }
            if(visited.contains(new reducedU(curr.x, curr.y, curr.d))){
                continue;
            }
            visited.add(new reducedU(curr.x, curr.y, curr.d));
            for(int i= 0 ;  i < 4; i++ ){
                int dc = 0;
                if(i == curr.d || (i+2)%4 ==curr.d){continue;}
                for(int j = 1; j<= max; j++){
                    Pair<Integer, Integer> dL = new Pair<>(curr.x + coordTransform.get(i).a() * j, curr.y + coordTransform.get(i).b() * j);
                    if(dL.a() >=0 && dL.a() < g.size()  && dL.b() >= 0 && dL.b() < g.get(0).size()){
                        dc += Integer.parseInt(String.valueOf(g.get(dL.a()).get(dL.b()).charValue()));
                        if (j< min){
                            continue;
                        }
                        int nc = curr.c + dc;
                        if(cMap.getOrDefault(new reducedU(dL.a(), dL.b(),i ), Integer.MAX_VALUE - 1) <= nc){
//                            System.out.println("continue");
                            continue;
                        }
                        cMap.put(new reducedU(dL.a(), dL.b(), i), nc);
                        q.add(new unit(nc, dL.a(), dL.b(), i));
                    }
                }
            }
        }
        return 0L;
    }

    public List<Pair<Integer, Integer>> coordTransform = new ArrayList<>(List.of(new Pair<>(0, 1), new Pair<>(1, 0), new Pair<>(0, -1), new Pair<>(-1, 0)));
    public ArrayList<ArrayList<Character>> g;
    @Override
    public String p1() throws IOException {
         g = new GetInputs(23,17).getAsCharArrayList();
        return  String.valueOf(djistrka(1,3));
    }


    @Override
    public String p2() throws IOException {
        return String.valueOf(djistrka(4,10));
    }







    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC17());
    }
}
