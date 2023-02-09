package javafiles.aoc21;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class AoC2 implements DAYID {
    public int horiz = 0;
    public int depth = 0;

    public int aim = 0;
    HashMap<String, Integer> map = new HashMap<String, Integer>();

    @Override
    public String p1() throws FileNotFoundException {
        GetInputs input = new GetInputs(21,2);
        while(input.hasLines()){
            String[] parts = input.nextLine().split(" ");
            if (parts[0].equals("forward")){
                horiz += Integer.parseInt(parts[1]);
            }else if(parts[0].equals("down")){
                depth -= Integer.parseInt(parts[1]);
            }else if (parts[0].equals("up")){
                depth += Integer.parseInt(parts[1]);
            }
        }
        return String.valueOf(Math.abs(horiz * depth));
    }

    @Override
    public String p2() throws FileNotFoundException {
        GetInputs input = new GetInputs(21,2);
        horiz = 0;
        depth = 0;
        aim = 0;
        while(input.hasLines()){
            String[] parts = input.nextLine().split(" ");
            if (parts[0].equals("forward")){
                horiz += Integer.parseInt(parts[1]);
                depth += Integer.parseInt(parts[1]) * aim;
            }else if(parts[0].equals("down")){
//                depth -= Integer.parseInt(parts[1]);
                aim += Integer.parseInt(parts[1]);
            }else if (parts[0].equals("up")){
//                depth += Integer.parseInt(parts[1]);
                aim -= Integer.parseInt(parts[1]);
            }
        }
        return String.valueOf(Math.abs(horiz * depth));
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC2());
    }
}
