package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AoC11 implements DAYID {

    @Override
    public String p1() throws IOException {
        int ans= 0 ;
        ArrayList<ArrayList<Character>> in = new GetInputs(23,11).getAsCharArrayList();
        ArrayList<Integer> rows = IntStream.range(0, in.size()).filter(i-> in.get(i).stream().allMatch(c-> c=='.')).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ArrayList<Character>> transposed = IntStream.range(0, in.get(0).size())
                .mapToObj(col -> IntStream.range(0, in.size())
                        .mapToObj(row -> in.get(row).get(col))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> col = IntStream.range(0,transposed.size()).filter(i-> transposed.get(i).stream().allMatch(c->c=='.')).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Pair<Integer,Integer>> positions = IntStream.range(0, in.size())
                .boxed()
                .flatMap(row -> IntStream.range(0, in.get(row).size())
                        .filter(c -> in.get(row).get(c) == '#')
                        .mapToObj(c -> new Pair<>(row, c)))
                .collect(Collectors.toCollection(ArrayList::new));

        for(int i = 0 ; i < positions.size(); i++){
            for(Pair<Integer,Integer> before : positions.subList(0,i)){
                for(int j = Math.min(positions.get(i).a(), before.a()); j < Math.max(positions.get(i).a(), before.a()); j++){
                    ans += (rows.contains(j))? 2: 1;
                }
                for(int j = Math.min(positions.get(i).b(), before.b()); j < Math.max(positions.get(i).b(), before.b()); j++){
                    ans += (col.contains(j))? 2: 1;
                }
            }
        }
        return String.valueOf(ans);
    }

    @Override
    public String p2() throws IOException {
        long ans= 0 ;
        ArrayList<ArrayList<Character>> in = new GetInputs(23,11).getAsCharArrayList();
        ArrayList<Integer> rows = IntStream.range(0, in.size()).filter(i-> in.get(i).stream().allMatch(c-> c=='.')).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ArrayList<Character>> transposed = IntStream.range(0, in.get(0).size())
                .mapToObj(col -> IntStream.range(0, in.size())
                        .mapToObj(row -> in.get(row).get(col))
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> col = IntStream.range(0,transposed.size()).filter(i-> transposed.get(i).stream().allMatch(c->c=='.')).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Pair<Integer,Integer>> positions = IntStream.range(0, in.size())
                .boxed()
                .flatMap(row -> IntStream.range(0, in.get(row).size())
                        .filter(c -> in.get(row).get(c) == '#')
                        .mapToObj(c -> new Pair<>(row, c)))
                .collect(Collectors.toCollection(ArrayList::new));

        for(int i = 0 ; i < positions.size(); i++){
            for(Pair<Integer,Integer> before : positions.subList(0,i)){
                for(int j = Math.min(positions.get(i).a(), before.a()); j < Math.max(positions.get(i).a(), before.a()); j++){
                    ans += (rows.contains(j))? 1000000L: 1L;
                }
                for(int j = Math.min(positions.get(i).b(), before.b()); j < Math.max(positions.get(i).b(), before.b()); j++){
                    ans += (col.contains(j))? 1000000L: 1L;
                }
            }
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC11());
    }
}

