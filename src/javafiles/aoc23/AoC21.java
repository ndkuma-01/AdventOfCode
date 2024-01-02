package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class AoC21 implements DAYID {
    ArrayList<Pair<Integer,Integer>> coordTransform = new ArrayList<>(List.of(new Pair<>(1,0), new Pair<>(0,1), new Pair<>(-1,0), new Pair<>(0,-1)));
    public record unit(int r, int c, int s){ }


    public String solve(ArrayList<Integer> steps)throws IOException{
        ArrayList<ArrayList<Character>> temp = new GetInputs(23,21).getAsCharArrayList();
        List<Integer> locOfS = new ArrayList<>();
        temp.forEach(x->{
            if(x.contains('S')){
                locOfS.add(temp.indexOf(x));
                locOfS.add(x.indexOf('S'));
            }
        });

        HashMap<Pair<Integer,Integer>, Integer> sets = new HashMap<>(Map.of(new Pair<>(locOfS.get(0), locOfS.get(1)), 0));
        Deque<Pair<Integer,Integer>> d = new ArrayDeque<>();
        d.add(new Pair<>(locOfS.get(0), locOfS.get(1)));
        int dist = 0;
        ArrayList<Integer> res = new ArrayList<>();
        while(dist < steps.stream().mapToInt(Integer::intValue).max().getAsInt()){
            dist++;
            Deque<Pair<Integer,Integer>> q = new ArrayDeque<>();
            while(!d.isEmpty()){
                Pair<Integer,Integer> temp1 = d.pop();
                for(Pair<Integer, Integer> trans : coordTransform){
                    int nx = temp1.a() + trans.a(), ny = temp1.b() + trans.b();
                    int tx = customMod(nx, temp.get(0).size()),  ty = customMod(ny, temp.size());
                    if(tx == -1 || ty == -1){
                        break;
                    }
//                    System.out.println("tx: "+  tx + " ty: " +ty );
                    if(temp.get(ty).get(tx) != '#' && !(sets.containsKey(new Pair<>(nx, ny)))){
                        sets.put(new Pair<>(nx, ny), dist);
                        q.add(new Pair<>(nx,ny));
                    }
                }

            }
            d = q;
            if(steps.contains(dist)){
                int finalDist = dist;
                res.add(sets.values().stream().filter(x-> x%2 == finalDist %2).collect(Collectors.toList()).size());
            }
        }
        return String.valueOf(res);



//
//
//
//        ArrayList<Pair<Integer, Integer>> v2 = new ArrayList<>();
//        HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
//        HashSet<Pair<Integer, Integer>> temp2 = new HashSet<>(Set.of(new Pair<>(locOfS.get(0),locOfS.get(1))));
//
//        d.add(new unit(locOfS.get(0), locOfS.get(1), steps));
//        while(!d.isEmpty()){
//            unit curr = d.pop();
//            if(curr.s %2==0){visited.add(new Pair<>(curr.r,curr.c)); v2.add(new Pair<>(curr.r, curr.c));}
//            if(curr.s == 0){continue;}
//
//
//            for(Pair<Integer,Integer> trans : coordTransform){
//                int newR = curr.r + trans.a(), newC = curr.c + trans.b();
//                if(newR<0 || newR>= temp.size() || newC < 0 || newC >= temp.get(0).size() || temp.get(newR).get(newC) == '#' ||  temp2.contains(new Pair<>(newR,newC))){
//                    continue;
//                }
//                temp2.add(new Pair<>(newR, newC));
//                d.add(new unit(newR, newC, curr.s - 1));
//            }
//        }
//
//
//        return String.valueOf(v2.size());

    }

    @Override
    public String p1() throws IOException {
      return solve(new ArrayList<>(List.of(64)));
    }

    @Override
    public String p2() throws IOException {
        Long targ = (26501365L-65L)/131L;
        System.out.println();
        List<Long> consts = Arrays.asList(solve(new ArrayList<>(List.of(65, 65 + 131, 65 + 131*2))).split("\\[")[1].split("]")[0].split(", ")).stream().mapToLong(x-> Long.parseLong(x.trim())).boxed().toList();
        List<Long> temp1 = List.of( ((consts.get(2) - 2* consts.get(1) + consts.get(0))/2)   ,  (consts.get(1) - consts.get(0) - ((consts.get(2) - 2* consts.get(1) + consts.get(0))/2)) , (consts.get(0)));
        System.out.println(consts);
        System.out.println(temp1);
        return String.valueOf((temp1.get(0) * targ * targ) + (temp1.get(1) * targ) + (temp1.get(2)));
    }
    public static int customMod(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        int result = a % b;
        return result >= 0 ? result : result + Math.abs(b);
    }


    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC21());
    }
}
