package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Aoc2 implements DAYID {
    @Override
    public String p1() throws FileNotFoundException {
        GetInputs in = new GetInputs(23,2);
        int sum = 0;
        while(in.hasLines()){
            String line = in.nextLine();
            int id = Integer.parseInt(line.split(":")[0].split( " ")[1].trim());
            line = line.split(":")[1];
            boolean goodGame = true;
            for(String games : line.split(";")){
                for(String hand :  games.split(",")) {
                    int numOfBalls = Integer.parseInt(hand.split(" ")[1].trim());
                    String col = hand.split(" ")[2].trim();
                    if (numOfBalls > Map.of("red", 12, "blue", 14, "green", 13).get(col)) {
                        goodGame = false;
                    }
                }
            }
            sum += (goodGame)? id : 0;
        }
        return String.valueOf(sum);
    }

    @Override
    public String p2() throws FileNotFoundException {
        GetInputs in = new GetInputs(23,2);
        int sum = 0;
        while(in.hasLines()){
            String line = in.nextLine().split(":")[1];
            HashMap<String, Integer> min = new HashMap<String,Integer>(Map.of("red", -1, "blue",-1, "green", -1));
            for(String games : line.split(";")){
                for(String hand :  games.split(",")) {
                    int numOfBalls = Integer.parseInt(hand.split(" ")[1].trim());
                    String col = hand.split(" ")[2].trim();
                    min.put(col, Math.max(min.get(col), numOfBalls));
                }
            }
            sum += (min.values().stream().reduce(1, (sub, el) -> sub * el));
        }
        return String.valueOf(sum);
    }


    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new Aoc2());
    }
}
