package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Card;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.LongStream;

public class AoC7 implements DAYID {
    ArrayList<String> types =  new ArrayList<>(List.of("high", "one", "two", "three", "full", "four", "five"));

    @Override
    public String p1() throws IOException {
        GetInputs in = new GetInputs(23,7);
        ArrayList<Card> cards = new ArrayList<>();
        while(in.hasLines()){
            String line = in.nextLine();
            cards.add(new Card(line.split(" ")[0].trim(), Long.parseLong(line.split(" ")[1]),false));
        }
        Collections.sort(cards);

        ArrayList<Integer> temp = new ArrayList<>(List.of(1,2));

        return String.valueOf(LongStream.range(0L, cards.size()).mapToObj(i -> cards.get((int)i).bid * (i+1)).mapToLong(Long::valueOf).sum());

    }

    @Override
    public String p2() throws IOException {
        GetInputs in = new GetInputs(23,7);
        ArrayList<Card> cards = new ArrayList<>();
        while(in.hasLines()){
            String line = in.nextLine();
            cards.add(new Card(line.split(" ")[0].trim(), Long.parseLong(line.split(" ")[1]),true));
        }
//
        Collections.sort(cards);

        return String.valueOf(LongStream.range(0L, cards.size()).mapToObj(i -> cards.get((int)i).bid * (i+1)).mapToLong(Long::valueOf).sum());
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC7());
    }




}

