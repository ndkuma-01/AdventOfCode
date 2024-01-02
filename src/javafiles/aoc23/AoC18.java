package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.*;

public class AoC18 implements DAYID {
    HashMap<Character, Pair<Integer, Integer>> stepMap = new HashMap<>(Map.of('R', new Pair<>(1,0), 'D', new Pair<>(0,1),'L', new Pair<>(-1,0), 'U', new Pair<>(0,-1), '0', new Pair<>(1,0), '1',new Pair<>(0,1), '2', new Pair<>(-1,0), '3', new Pair<>(0,-1)));
    public long compute(List<Pair<Pair<Integer, Integer>, Double>> data){
       double pos = 0,  res= 1;
       for(Pair<Pair<Integer,Integer>, Double> x : data){
           pos += x.a().a() * x.b();
           res += x.a().b()*x.b() * pos + x.b()/2;
       }

        return (long)res;
    }
    @Override
    public String p1() throws IOException {
        List<List<String>> grid = new GetInputs(23, 18).filetoArrayList().stream().map(x -> Arrays.asList(x.split(" "))).toList();
        List<Pair<Pair<Integer, Integer>, Double>> temp = new ArrayList<>();
        grid.stream().forEach(x->{temp.add(new Pair<>(stepMap.get(x.get(0).charAt(0)), Double.parseDouble(x.get(1).trim())));});
        return String.valueOf(compute(temp));

    }

    @Override
    public String p2() throws IOException {
        List<List<String>> grid = new GetInputs(23, 18).filetoArrayList().stream().map(x -> Arrays.asList(x.split(" "))).toList();
        List<Pair<Pair<Integer, Integer>, Double>> temp = new ArrayList<>();
        grid.stream().forEach(x->{temp.add(new Pair<>(stepMap.get(x.get(2).charAt(7)),  (double)hexToDec(x.get(2).substring(2,7)) ));});
        return String.valueOf(compute(temp));
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC18());
    }


    public static int hexToDec(String hex)
    {
        int len = hex.length();
        int dec = 0;
        for (int i = 0; i < len; i++) {
            char c = hex.charAt(i);
            int digit = Character.digit(c, 16);
            dec = dec * 16 + digit;
        }
        return dec;
    }
}
