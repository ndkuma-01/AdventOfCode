package javafiles.aoc15;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AoC2 extends RUNDAY implements DAYID {


    @Override
    public String p1() throws FileNotFoundException {
        GetInputs input = new GetInputs(15,2);
        int answer = 0;
        while(input.hasLines()){
            String[] parts = input.nextLine().split("x");
            ArrayList <Integer> areas = new ArrayList<Integer>();
            areas.add(Integer.parseInt(parts[0]) *Integer.parseInt(parts[1]));
            areas.add(Integer.parseInt(parts[0]) *Integer.parseInt(parts[2]));
            areas.add(Integer.parseInt(parts[1]) *Integer.parseInt(parts[2]));
            Collections.sort(areas);

            answer += areas.get(0) + 2 * (Integer.parseInt(parts[0]) *Integer.parseInt(parts[1])) + 2* Integer.parseInt(parts[1]) *Integer.parseInt(parts[2]) + 2 * Integer.parseInt(parts[0]) *Integer.parseInt(parts[2]);


        }

        return String.valueOf(answer);
    }

    @Override
    public String p2() throws FileNotFoundException {
        GetInputs input = new GetInputs(15,2);
        int answer = 0;
        while(input.hasLines()) {
            String[] parts = input.nextLine().split("x");
            int l = Integer.parseInt(parts[0]);
            int w = Integer.parseInt(parts[1]);
            int h = Integer.parseInt(parts[2]);
            ArrayList<Integer> dimensions = new ArrayList<Integer>();
            dimensions.add(l);
            dimensions.add(w);
            dimensions.add(h);
            Collections.sort(dimensions);
            answer += 2 * dimensions.get(0) + 2 * dimensions.get(1) + (dimensions.get(0) * dimensions.get(1) * dimensions.get(2));

        }
        return String.valueOf(answer);
    }


    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC2());
    }

}
