package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AoC15 implements DAYID {

    public int hash(String line){
        int sum = 0;
        for(int j = 0 ; j < line.length(); j++){
            char curr = line.charAt(j);
            sum = ((sum + (int)curr)*17)%256;
        }

        return sum;
    }

    @Override
    public String p1() throws IOException {
        return String.valueOf(Arrays.asList(new GetInputs(23,15).nextLine().split(",")).stream().mapToInt(x-> hash(x)).sum());
    }

    @Override
    public String p2() throws IOException {
        List<String> hashes =  Arrays.asList(new GetInputs(23,15).nextLine().trim().split(","));
        List<List<Pair<String,Integer>>> bs = new ArrayList<>();
        for(int i = 0 ; i < 256; i++){bs.add(new ArrayList<Pair<String,Integer>>());}
        for(String c : hashes){
            if(c.indexOf('-') >0){
                String n = c.substring(0, c.length() -1);
                for (int i = 0; i < bs.get(hash(n)).size(); i++) {
                    if (bs.get(hash(n)).get(i).getLeft().equals(n)) {
                        bs.get(hash(n)).remove(i);
                    }
                }
            }else{
                String n = c.substring(0, c.length() - 2);
                int hash = hash(n), arg = c.charAt(c.length() - 1) - '0';
                List<String> names = bs.get(hash).stream().map(x -> x.getLeft()).toList();
                if(names.contains(n)){
                    bs.get(hash).set(names.indexOf(n), new Pair<String, Integer>(n, arg));
                }else{
                    bs.get(hash).add(new Pair<String, Integer>(n, arg));
                }
            }
        }
        return String.valueOf(IntStream.range(0, bs.size()).mapToObj(i -> IntStream.range(0, bs.get(i).size()).mapToDouble(j -> (i + 1) * (j + 1) * bs.get(i).get(j).b()).sum()).mapToInt(Double::intValue).sum());
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC15());
    }
}
