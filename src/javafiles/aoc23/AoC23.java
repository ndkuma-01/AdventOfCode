package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.util.*;

public class AoC23 implements DAYID {
    List<Pair<Integer, Integer>> coordTransform = new ArrayList<>(List.of(new Pair<>(-1, 0), new Pair<>(1, 0), new Pair<>(0, -1), new Pair<>(0, 1)));
    HashMap<Character, List<Pair>> dirToCoor = new HashMap<>(Map.of('^', List.of(new Pair<>(-1, 0)), 'v', List.of(new Pair<>(1, 0)), '<', List.of(new Pair<>(0, -1)), '>', List.of(new Pair<>(0, 1)), '.', List.of(new Pair<>(-1, 0), new Pair<>(1, 0), new Pair<>(0, -1), new Pair<>(0, 1))));
    HashSet<Pair<Integer,Integer>> dfsSet = new HashSet<>();
     Pair<Integer,Integer> s, e;
     int p = 0;
    HashMap<Pair<Integer, Integer>, HashMap<Pair<Integer, Integer>, Integer>> weightG = new HashMap<>();
    public int dfs(Pair<Integer, Integer> curr){
        System.out.println("ran" + p++);
        if(curr.equals(e)){return 0;}
        int cost = Integer.MIN_VALUE;
        dfsSet.add(curr);
//        if(weightG.get(curr) != null) {
            for (Pair<Integer, Integer> n : weightG.get(curr).keySet()) {
//                System.out.println(n);
                if (!dfsSet.contains(n)) {
                    cost = Math.max(cost, dfs(n) + weightG.get(curr).get(n));
                }
            }
//        }
        dfsSet.remove(curr);
        return cost;
    }

    @Override
    public String p1() throws IOException {
        var g = new GetInputs(23,23).getAsCharArrayList();
        s = new Pair<>(0,g.get(0).indexOf('.'));
        e = new Pair<>(g.size() - 1, g.get(g.size() - 1).indexOf('.'));
        List<Pair<Integer, Integer>> locs = new ArrayList<>(List.of(s,e));
        for(int i =0  ; i < g.size(); i++){
            for(int j = 0 ; j < g.get(i).size(); j++){
                if(g.get(i).get(j) == '#'){continue;}
                int n = 0;
                for(Pair<Integer,Integer> b : coordTransform){
                    Pair<Integer, Integer> delta = new Pair<>(i + b.a(), j + b.b());
                    if(0 <= delta.a() && delta.a() < g.size() && 0<= delta.b() && delta.b() < g.get(0).size() && g.get(delta.a()).get(delta.b()) != '#'){
                        n++;
                    }
                }
                if(n >= 3){
                    locs.add(new Pair<Integer,Integer>(i,j));
                }
            }
        }
        locs.stream().forEach(x-> weightG.put(x, new HashMap<>()));
//        System.out.println("initialized weightG" + weightG.size());

        for(int i =0  ; i < locs.size(); i++){
            Deque<List<Integer>> stack = new ArrayDeque<>();
            stack.add(List.of(0,locs.get(i).a(), locs.get(i).b()));
            HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
            visited.add(new Pair<>(locs.get(0).a(), locs.get(0).b()));

            while(!stack.isEmpty()){
                List<Integer> curr = stack.pop();
//                System.out.println("Stack length: " + stack.size());
//                System.out.println(curr);
                if (curr.get(0) != 0 && locs.contains(new Pair<>(curr.get(1), curr.get(2)))) {
//                    System.out.println("first");
                    weightG.put(new Pair<>(locs.get(i).a(), locs.get(i).b()), weightG.getOrDefault(new Pair<>(locs.get(i).a(), locs.get(i).b()), new HashMap<>())).put(new Pair<>(curr.get(1), curr.get(2)), curr.get(0));
                    continue;
                }
                for(Pair<Integer,Integer> trans : dirToCoor.get(g.get(curr.get(1)).get(curr.get(2)))){
                    int nr = curr.get(1) + trans.a(), nc = curr.get(2) + trans.b();
//                    System.out.println("(nr nc): (" + nr + " " + nc + ")");
                    if((0<= nr) && (nr < g.size() )&&( 0<= nc )&& (nc< g.get(0).size()) && (g.get(nr).get(nc) != '#') && (!visited.contains(new Pair<>(nr,nc)))){
//                        System.out.println("second");
                        stack.add(List.of(curr.get(0) + 1, nr, nc));
                        visited.add(new Pair<>(nr,nc));
                    }
                }
            }
        }
//        System.out.println(weightG);
        return String.valueOf(dfs(s));
    }

    @Override
    public String p2() throws IOException {
        dfsSet.clear();
        var g = new GetInputs(23,23).getAsCharArrayList();
        List<Pair<Integer, Integer>> locs = new ArrayList<>(List.of(s,e));
        for(int i =0  ; i < g.size(); i++){
            for(int j = 0 ; j < g.get(i).size(); j++){
                if(g.get(i).get(j) == '#'){continue;}
                int n = 0;
                for(Pair<Integer,Integer> b : coordTransform){
                    Pair<Integer, Integer> delta = new Pair<>(i + b.a(), j + b.b());
                    if(0 <= delta.a() && delta.a() < g.size() && 0<= delta.b() && delta.b() < g.get(0).size() && g.get(delta.a()).get(delta.b()) != '#'){
                        n++;
                    }
                }
                if(n >= 3){
                    locs.add(new Pair<Integer,Integer>(i,j));
                }
            }
        }
        locs.stream().forEach(x-> weightG.put(x, new HashMap<>()));
        System.out.println("initialized weightG" + weightG.size());

        for(int i =0  ; i < locs.size(); i++){
            Deque<List<Integer>> stack = new ArrayDeque<>();
            stack.add(List.of(0,locs.get(i).a(), locs.get(i).b()));
            HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
            visited.add(new Pair<>(locs.get(0).a(), locs.get(0).b()));

            while(!stack.isEmpty()){
                List<Integer> curr = stack.pop();
                System.out.println("Stack length: " + stack.size());
                System.out.println(curr);
                if (curr.get(0) != 0 && locs.contains(new Pair<>(curr.get(1), curr.get(2)))) {
                    System.out.println("first");
                    weightG.put(new Pair<>(locs.get(i).a(), locs.get(i).b()), weightG.getOrDefault(new Pair<>(locs.get(i).a(), locs.get(i).b()), new HashMap<>())).put(new Pair<>(curr.get(1), curr.get(2)), curr.get(0));
                    continue;
                }


                for(Pair<Integer,Integer> trans : coordTransform){
                    int nr = curr.get(1) + trans.a(), nc = curr.get(2) + trans.b();
                    System.out.println("(nr nc): (" + nr + " " + nc + ")");
                    if((0<= nr) && (nr < g.size() )&&( 0<= nc )&& (nc< g.get(0).size()) && (g.get(nr).get(nc) != '#') && (!visited.contains(new Pair<>(nr,nc)))){
                        System.out.println("second");
                        stack.add(List.of(curr.get(0) + 1, nr, nc));
                        visited.add(new Pair<>(nr,nc));
                    }
                }
            }
        }
//        System.out.println(weightG);
        return String.valueOf(dfs(s));
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC23());
    }
}
