package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;
import Utilities.aoc23.Parts;
import Utilities.aoc23.UniversalAtomic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class AoC10 implements DAYID {
    Pair<Integer, Integer> D = new Pair<>(1,0),U  = new Pair<>(-1,0), L = new Pair<>(0,-1), R = new Pair<>(0, 1);
    public HashMap<ArrayList<Pair<Integer,Integer>>, Character> startMap = new HashMap<>(Map.of( new ArrayList<>(List.of(U, D)) , '|',  new ArrayList<>(List.of(L,R)) , '-', new ArrayList<>(List.of(U,L)), 'J', new ArrayList<>(List.of(U, R)), 'L', new ArrayList<>(List.of(D,L)), '7')  );
    public Pair<HashSet<Pair<Integer,Integer>>,Integer> fill(ArrayList<ArrayList<Character>> g, int sR, int sC) {
        List<Pair<Integer,Integer>> direct = new ArrayList<>(List.of(U,D,L,R));
        List<String> pipeCombos = new ArrayList<>(List.of("|F7", "|LJ", "-FL", "-J7"));
        List<Pair<Integer, Integer>> matches = new ArrayList<>();
        for(int i = 0 ; i < direct.size(); i++){
            int r = sR + direct.get(i).a(), c = sC + direct.get(i).b();
            if(pipeCombos.get(i).indexOf(g.get(r).get(c))  >-1){
                matches.add(new Pair<>(direct.get(i).a(), direct.get(i).b()));
            }

        }
        char start = startMap.getOrDefault(matches, 'F');
        System.out.println(start);
        int r = sR,  c = sC;
        Pair<Integer, Integer> delta = matches.get(0);
        HashSet<Pair<Integer,Integer>> visited = new HashSet<>(Set.of(new Pair<>(r,c)));
        int i = 0;
        while(true){
            i++;
            r = r + delta.a();
            c = c + delta.b();
            visited.add(new Pair<>(r,c));

            if("L7".indexOf(g.get(r).get(c)) >-1){
                delta = new Pair<>(delta.b(), delta.a());
            } else if ("FJ".indexOf(g.get(r).get(c)) > -1) {
                delta = new Pair<>(-1*delta.b(), -1*delta.a());
            }else if(g.get(r).get(c) == 'S'){
                break;
            }
        }
        g.get(sR).set(sC, start);
        return new Pair<>(visited,i);
    }
    public Integer enclosedArea(ArrayList<ArrayList<Character>> g,HashSet<Pair<Integer, Integer>> main){
            int area = 0;

            for(int i = 0; i < g.size(); i++){
                ArrayList<Character> currR = g.get(i);
                boolean in = false;
                for(int j = 0; j < currR.size(); j++){
                    if(!main.contains(new Pair<>(i,j))){
                        area += (in)? 1:0;
                    }else{
                        in = in ^ ("|F7".indexOf(currR.get(j)) > -1 );
                    }
                }
            }
            return area;
    }

        ArrayList<ArrayList<Character>> g;
        HashSet<Pair<Integer,Integer>> main;
    @Override
    public String p1() throws IOException {
         g =  new GetInputs(23,10).getAsCharArrayList();
        UniversalAtomic<Pair<Integer, Integer>> start = new UniversalAtomic<>();
        g.stream().forEach(x -> {if(x.contains('S')){start.setAtom(new Pair<>(g.indexOf(x), x.indexOf('S')));}});
        Pair<HashSet<Pair<Integer,Integer>>,Integer> temp = fill(g, start.getAtom().a(), start.getAtom().b());
        main = temp.a();
        return String.valueOf(temp.b());
    }

    @Override
    public String p2() throws IOException {
        return  String.valueOf( enclosedArea(g,main));
    }

    public static void main(String[] args) throws IOException {

        RUNDAY.run(new AoC10());
    }
}
