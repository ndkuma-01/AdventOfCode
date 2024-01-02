package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.UniversalAtomic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AoC1 implements DAYID {
    @Override
    public String p1() throws IOException {
        ArrayList<Integer> nums = new ArrayList<>();
        new GetInputs(23,1).filetoArrayList().forEach(y-> {
            UniversalAtomic<String> res = new UniversalAtomic<>("");
            res.setAtom(res.getAtom().concat(IntStream.range(0, y.length()).mapToObj(y::charAt).filter(i -> i - '0' >= 0 && i - '0' <= 9).toList().stream().map(String::valueOf).collect(Collectors.joining(""))));
            nums.add(Integer.parseInt(res.getAtom().substring(0, 1).concat( res.getAtom().substring(res.getAtom().length() - 1))));});
        return String.valueOf(nums.stream().mapToInt(Integer::intValue).sum());
    }
    @Override
    public String p2() throws IOException {
        ArrayList<Integer> nums = new ArrayList<>();
        String[] arr = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        new GetInputs(23,1).filetoArrayList().forEach(line->{
            UniversalAtomic<String> res = new UniversalAtomic<>("");
            IntStream.range(0, line.length()).forEach(i-> {res.setAtom(res.getAtom() + ((line.charAt(i) - '0' >= 0 && line.charAt(i) - '0' <= 9)? "" + line.charAt(i) : ""));
                if (IntStream.range(0, arr.length).mapToObj(x -> line.substring(i).startsWith(arr[x])).toList().contains(true)) {res.setAtom(res.getAtom().concat("" +  (IntStream.range(0, arr.length).mapToObj(x -> line.substring(i).startsWith(arr[x])).toList().indexOf(true) + 1)));}
            });
            nums.add(Integer.parseInt(res.getAtom().substring(0, 1) + res.getAtom().substring(res.getAtom().length() - 1)));
        });
        return String.valueOf(nums.stream().mapToInt(Integer::intValue).sum());
    }
    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC1());
    }
}
