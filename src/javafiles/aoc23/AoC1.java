package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class AoC1 implements DAYID {




    @Override
    public String p1() throws FileNotFoundException {
        GetInputs input = new GetInputs(23, 1);
        ArrayList<Integer> nums = new ArrayList<Integer>();
        while (input.hasLines()) {
            String line = input.nextLine();
            String result = "";
            List<Character> temp = IntStream.range(0, line.length()).mapToObj(i -> line.charAt(i)).filter(i -> i - '0' >= 0 && i - '0' <= 9).toList();
            for(int i = 0 ; i < temp.size(); i++) {
                result += "" + temp.get(i);
            }
            nums.add(Integer.parseInt(result.substring(0, 1) + result.substring(result.length() - 1)));
        }
        return String.valueOf(nums.stream().mapToInt(x -> x).sum());
    }

    @Override
    public String p2() throws FileNotFoundException {
        GetInputs input = new GetInputs(23, 1);
        ArrayList<Integer> nums = new ArrayList<Integer>();
        String[] arr = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        while (input.hasLines()) {
            String line = input.nextLine();
            String result = "";
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) - '0' >= 0 && line.charAt(i) - '0' <= 9) {
                    result += "" + line.charAt(i);
                }
                String curr = line.substring(i);
                for (int j = 0; j < arr.length; j++) {
                    if (curr.startsWith(arr[j])) {
                        int temp = j + 1;
                        result += "" + temp;
                    }
                }
            }
                nums.add(Integer.parseInt(result.substring(0, 1) + result.substring(result.length() - 1)));
        }
        return String.valueOf(nums.stream().mapToInt(x -> x).sum());
    }


    public static void main(String[] args) throws FileNotFoundException {

        RUNDAY.run(new AoC1());
    }
}
