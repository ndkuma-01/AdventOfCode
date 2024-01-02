package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AoC4 implements DAYID {
    @Override
    public String p1() throws IOException {
        AtomicInteger i = new AtomicInteger(0);
        new GetInputs(23,4).filetoArrayList().forEach(line ->{
            String[] numGroups = line.split(":")[1].trim().split("\\|");
            ArrayList<Integer> winNums = new ArrayList<>(Arrays.stream(numGroups[0].trim().split("\\s+")).map(Integer::parseInt).toList()), ourNums = new ArrayList<>(Arrays.stream(numGroups[1].trim().split("\\s+")).map(Integer::parseInt).toList()),  sharedNums = new ArrayList<>(ourNums.stream().filter(winNums::contains).toList());
            i.set(i.get() + ((sharedNums.size() > 0)? (int) Math.pow(2, sharedNums.size() - 1) : 0));
        });
        return String.valueOf(i.get());
    }

    @Override
    public String p2() throws IOException {
        HashMap<Integer, Integer> extra = new HashMap<>();
        IntStream.range(0, 218).forEach(i -> extra.put(i,1));
        AtomicInteger i = new AtomicInteger(0);  // <-- finally understand the use for these
        new GetInputs(23,4).filetoArrayList().forEach(line->{
            String[] numGroups = line.split(":")[1].trim().split("\\|");
            ArrayList<Long> winNums = new ArrayList<>(Arrays.stream(numGroups[0].trim().split("\\s+")).map(Long::parseLong).toList()),  ourNums = new ArrayList<>(Arrays.stream(numGroups[1].trim().split("\\s+")).map(Long::parseLong).toList()), sharedNums = new ArrayList<>(ourNums.stream().filter(winNums::contains).toList());
            IntStream.range(i.get()+1, i.get()+sharedNums.size() + 1).forEach(j -> extra.put(j, extra.getOrDefault(j, 0) + extra.getOrDefault(i.get(),0)));
            i.getAndIncrement();
        });
        return String.valueOf(extra.values().stream().mapToInt(Integer::intValue).sum());
    }


    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC4());
    }
}
