package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.General.StringUtils;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Utilities.General.StringUtils.convertToGrid;

public class AoC13 implements DAYID {

    public Long score(List<List<Character>> g, boolean p1){
        for(int i = 1; i<g.size(); i++){
            List<List<Character>> above = new ArrayList<>(g.subList(0,i));
            List<List<Character>> below = new ArrayList<>(g.subList(i, g.size()));
            int min = Math.min(above.size(), below.size());

            above = above.subList(i - min,i);
            below = below.subList(0, min);
            Collections.reverse(above);

            if(p1) {
                if (above.size() == 0 && below.size() == 0) {
                    continue;
                }

                if (above.equals(below)) {
                    return i + 0L;
                }
            }else{
                int overallSum = 0;
                for(int j = 0 ; j < above.size(); j++){
                    List<Character> x =  above.get(j);
                    List<Character> y = below.get(j);
                    int sum = 0;
                    for(int k = 0; k < x.size(); k++){
                        Character a = x.get(k);
                        Character b = y.get(k);
                        sum += (a==b)? 0 : 1;
                    }
                    overallSum += sum;
                }
                if(overallSum == 1){
                    return i + 0L;
                }
            }
        }
        return 0L;
    }


    @Override
    public String p1() throws IOException {
        ArrayList<String> lines = new GetInputs(23,13).filetoArrayList();
        List<Integer> blanks = new ArrayList<>();
        blanks.add(-1);
        IntStream.range(0, lines.size()).filter(i -> lines.get(i).equals("")).forEach(x -> blanks.add(x));
        blanks.add(lines.size());
        Long sum = 0L;
        for(int i = 0; i < blanks.size()-1; i++){
            List<List<Character>> grid = convertToGrid(IntStream.range(blanks.get(i) + 1, blanks.get(i+1)).mapToObj(j -> lines.get(j)).collect(Collectors.toList()));
            sum += (score(grid,true) * 100);
//            System.out.println("Sum before cols: " + sum);
            sum += score(IntStream.range(0, grid.get(0).size()).mapToObj(j->grid.stream().map(row->row.get(j)).collect(Collectors.toList())).collect(Collectors.toList()), true);
//            System.out.println(IntStream.range(0, grid.get(0).size()).mapToObj(j->grid.stream().map(row->row.get(j)).collect(Collectors.toList())).collect(Collectors.toList()));
        }
        return String.valueOf(sum);
    }

    @Override
    public String p2() throws IOException {
        ArrayList<String> lines = new GetInputs(23,13).filetoArrayList();
        List<Integer> blanks = new ArrayList<>();
        blanks.add(-1);
        IntStream.range(0, lines.size()).filter(i -> lines.get(i).equals("")).forEach(x -> blanks.add(x));
        blanks.add(lines.size());
        Long sum = 0L;
        for(int i = 0; i < blanks.size()-1; i++){
            List<List<Character>> grid = convertToGrid(IntStream.range(blanks.get(i) + 1, blanks.get(i+1)).mapToObj(j -> lines.get(j)).collect(Collectors.toList()));
            sum += (score(grid,false) * 100);
//            System.out.println("Sum before cols: " + sum);
            sum += score(IntStream.range(0, grid.get(0).size()).mapToObj(j->grid.stream().map(row->row.get(j)).collect(Collectors.toList())).collect(Collectors.toList()), false);
//            System.out.println(IntStream.range(0, grid.get(0).size()).mapToObj(j->grid.stream().map(row->row.get(j)).collect(Collectors.toList())).collect(Collectors.toList()));
        }
        return String.valueOf(sum);
    }

    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC13());
    }
}
