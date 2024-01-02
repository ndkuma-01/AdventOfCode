package javafiles.aoc15;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AoC2 extends RUNDAY implements DAYID {


    @Override
    public String p1() throws IOException {
        GetInputs input = new GetInputs(15,2);
        int answer = 0;
        while(input.hasLines()){
            String[] parts = input.nextLine().split("x");
            ArrayList <Integer> areas = new ArrayList<Integer>(Arrays.asList(Integer.parseInt(parts[0]) *Integer.parseInt(parts[1]),Integer.parseInt(parts[0]) *Integer.parseInt(parts[2]),Integer.parseInt(parts[1]) *Integer.parseInt(parts[2])));
            Collections.sort(areas);
            answer += areas.get(0) + 2 * (Integer.parseInt(parts[0]) *Integer.parseInt(parts[1])) + 2* Integer.parseInt(parts[1]) *Integer.parseInt(parts[2]) + 2 * Integer.parseInt(parts[0]) *Integer.parseInt(parts[2]);
        }
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws IOException {
        GetInputs input = new GetInputs(15,2);
        int answer = 0;
        while(input.hasLines()) {
            String[] parts = input.nextLine().split("x");
            ArrayList<Integer> d = new ArrayList<Integer>(Arrays.asList(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2])));
            Collections.sort(d);
            answer += 2 * d.get(0) + 2 * d.get(1) + (d.get(0) * d.get(1) * d.get(2));

        }
        return String.valueOf(answer);
    }


    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC2());
    }

}
