package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
public class AoC6 implements DAYID {
    @Override
    public String p1() throws IOException {
        GetInputs in = new GetInputs(23,6);
        ArrayList<Integer> times = new ArrayList<>(Arrays.asList(in.nextLine().split(":")[1].split("\\s+")).stream().skip(1).map(x -> Integer.parseInt(x.trim())).collect(Collectors.toList()));
        ArrayList<Integer> distances = new ArrayList<>(Arrays.asList(in.nextLine().split(":")[1].split("\\s+")).stream().skip(1).map(x-> Integer.parseInt(x.trim())).toList());
        Long res = 1L ;
        for(int i = 0 ; i <times.size(); i++){
            int currT = times.get(i), currDToBeat = distances.get(i);
            res *= IntStream.range(0, currT).mapToObj(x -> x * (currT - x) > currDToBeat).toList().stream().filter(Boolean::booleanValue).toList().size();
        }
        return String.valueOf(res);
    }
    @Override
    public String p2() throws IOException {
        GetInputs in = new GetInputs(23,6);
        Long times  = Long.parseLong(Arrays.asList(in.nextLine().split(":")[1].split("\\s+")).stream().skip(1).collect(Collectors.joining()).trim());
        Long dist = Long.parseLong(Arrays.asList(in.nextLine().split(":")[1].split("\\s+")).stream().skip(1).collect(Collectors.joining()).trim());
        int s = LongStream.range(0L, times).mapToObj(i -> i * (times - i) > dist).toList().indexOf(true);
        int e = LongStream.range(0L, times).mapToObj(i -> i * (times - i) > dist).toList().lastIndexOf(true);
        return String.valueOf(e - s + 1);
    }
    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC6());
    }
}
