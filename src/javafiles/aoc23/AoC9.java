package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.General.StreamUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class AoC9 implements DAYID {
    //One liners are fun! :D :D :D :D
    @Override
    public String p1() throws IOException {
        return String.valueOf( new GetInputs(23,9).filetoArrayList().stream().map(x -> Arrays.stream(x.split("\\s+")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList())).mapToLong(x -> compute((ArrayList<Long>) x)).sum());
    }
    @Override
    public String p2() throws IOException {
        return String.valueOf( new GetInputs(23,9).filetoArrayList().stream().map(x -> StreamUtils.reverseStream( Arrays.stream(x.trim().split("\\s+")).mapToLong(Long::parseLong).boxed()).collect(Collectors.toList())).mapToLong(x->compute((ArrayList<Long>) x)).sum());
    }


    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC9());
    }
    public static long compute(ArrayList<Long> a){
        ArrayList<ArrayList<Long>> seqs = new ArrayList<>(List.of(a));
        AtomicInteger i = new AtomicInteger(0);
        while(true){
            ArrayList<Long> temp = new ArrayList<>();
            IntStream.range(0,seqs.get(i.get()).size() - 1).forEach(j -> temp.add(seqs.get(i.get()).get(j+1) - seqs.get(i.get()).get(j)));
            seqs.add(temp);
            if(temp.stream().filter(x-> x==0L).toList().size() == temp.size()){
                break;
            }
            i.getAndIncrement();
        }
        return seqs.stream().mapToLong(seq -> seq.get(seq.size() - 1)).sum();
    }
}
