package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AoC16 implements DAYID {
    ArrayList<String> g;

    public int compute(int a, int b, int c, int d){
        Deque<ArrayList<Integer>> q = new ArrayDeque<>();
        q.add(new ArrayList<>(List.of(a,b,c,d)));
        HashSet<String> visited = new HashSet<>();
        while(!q.isEmpty()){
//            System.out.println(q);
            ArrayList<Integer> curr= q.pop();
//            System.out.println("before: " + curr);
            curr.set(0, curr.get(0) + curr.get(2));
            curr.set(1, curr.get(1) + curr.get(3));
//            System.out.println("after: " +curr);
            if(curr.get(0) < 0 || curr.get(0) >= g.size() || curr.get(1) < 0 || curr.get(1) >= g.get(0).length()){continue;}
            char currChar = g.get(curr.get(0)).charAt(curr.get(1));

            if(currChar == '.' || (currChar == '-' && curr.get(3) != 0) || (currChar == '|' && curr.get(2) != 0)){
//                System.out.println("first");
                if(!visited.contains(curr.toString())){
                    visited.add(curr.toString()); q.add(curr);
                }
            }else if(currChar == '/'){
//                System.out.println("second");
                int temp = curr.get(2);
                curr.set(2, curr.get(3) * -1);
                curr.set(3, temp * -1);
                if(!visited.contains(curr.toString())){
                    visited.add(curr.toString()); q.add(curr);
                }
            } else if (currChar == '\\') {
//                System.out.println("third");
                int temp = curr.get(2);
                curr.set(2, curr.get(3));
                curr.set(3, temp );
                if(!visited.contains(curr.toString())){
                    visited.add(curr.toString()); q.add(curr);
                }
            }else{
                ArrayList<Integer> coordTransform =  (currChar == '|')? new ArrayList<>(List.of(1,0,-1,0)) : new ArrayList<>(List.of(0,1,0,-1));
//                System.out.println("coord trasnform: " + coordTransform);
                if (!visited.contains(new ArrayList<>(List.of(curr.get(0), curr.get(1), coordTransform.get(0), coordTransform.get(1))).toString())) {
//                    System.out.println("fourth");

                    visited.add(new ArrayList<>(List.of(curr.get(0), curr.get(1), coordTransform.get(0), coordTransform.get(1))).toString());
                    q.add(new ArrayList<>(List.of(curr.get(0), curr.get(1), coordTransform.get(0), coordTransform.get(1))));
                }
                if (!visited.contains(new ArrayList<>(List.of(curr.get(0), curr.get(1), coordTransform.get(2), coordTransform.get(3))).toString())) {
                    visited.add(new ArrayList<>(List.of(curr.get(0), curr.get(1), coordTransform.get(2), coordTransform.get(3))).toString());
                    q.add(new ArrayList<>(List.of(curr.get(0), curr.get(1), coordTransform.get(2), coordTransform.get(3))));
                }
            }

        }
        HashSet<String> unique = new HashSet<>();
//        System.out.println(visited.size());
//        System.out.println(visited.stream().distinct().collect(Collectors.toList()));
        for(String curr : visited){
            String[] temp = curr.split("\\[")[1].split("]")[0].split(", ");

            unique.add(temp[0] + ", "+ temp[1]);
        }
        return unique.size();
    }


    @Override
    public String p1() throws IOException {
        g = new GetInputs(23,16).filetoArrayList();
        return String.valueOf(compute(0,-1,0,1));
    }

    @Override
    public String p2() throws IOException {
        int max = 0;
        for(int i = 0 ; i < g.size(); i++){
            max = Math.max(max, compute(i,-1,0,1));
            max = Math.max(max, compute(i,g.get(0).length(),0,-1));
        }
        for(int i = 0 ; i < g.size(); i++){
            max = Math.max(max, compute(-1, i, 1, 0));
            max = Math.max(max, compute(g.size(), i, -1, 0));

        }
        return String.valueOf(max);
    }

    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC16());

    }
}
