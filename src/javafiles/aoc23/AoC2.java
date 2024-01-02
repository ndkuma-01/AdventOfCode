package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AoC2 implements DAYID {
    @Override
    public String p1() throws IOException {
        AtomicInteger sum = new AtomicInteger(0);
        new GetInputs(23,2).filetoArrayList().forEach(line -> {
            int id = Integer.parseInt(line.split(":")[0].split(" ")[1].trim());
            boolean goodGame = true;
            for(String games :  line.split(":")[1].split(";")) {
                for (String hand : games.split(",")) {
                    goodGame = (Integer.parseInt(hand.split(" ")[1].trim()) > Map.of("red", 12, "blue", 14, "green", 13).get(hand.split(" ")[2].trim()))? false : goodGame;
                }
            }
            sum.set(sum.get() + (((goodGame)? id:0)));
        });
        return String.valueOf(sum.get());
    }
    @Override
    public String p2() throws IOException {
        AtomicInteger sum = new AtomicInteger(0);
        new GetInputs(23,2).filetoArrayList().stream().map(l -> l.split(":")[1]).forEach(line ->{
            HashMap<String, Integer> min = new HashMap<>(Map.of("red", -1, "blue",-1, "green", -1));
            for(String games : line.split(";")){
                for(String hand :  games.split(",")) {
                    min.put(hand.split(" ")[2].trim(), Math.max(min.get(hand.split(" ")[2].trim()), Integer.parseInt(hand.split(" ")[1].trim())));
                }
            }
            sum.set(sum.get() + min.values().stream().reduce(1, (sub, el) -> sub * el));
        });
        return String.valueOf(sum.get());
    }


    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC2());
    }
}
