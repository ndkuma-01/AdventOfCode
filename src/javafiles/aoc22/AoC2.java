package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AoC2 implements DAYID {
    @Override
    public String p1() throws IOException {
        //gets the inputs for the second day
        GetInputs INPUT1 = new GetInputs(22,2);
        //creates two arrays with the possible options of both the opponent and you (the player)
        String[] opponent = {"A", "B", "C"};
        String[] u = {"X", "Y", "Z"};
        //creates an arraylist with the totals of each game
        ArrayList<Integer> totals = new ArrayList<Integer>();
        //iterates through each line
        while (INPUT1.hasLines()) {
            //takes in the play played by the opponent and you
            String opp = INPUT1.next();
            String you = INPUT1.next();
            int oppPlace = 0;
            int youPlace = 0;
            //figures out what index in the array was played by the opponent
            //this could be made much more efficient with a hashmap
            for (int i = 0; i < opponent.length; i++) {
                if (opponent[i].equals(opp)) {
                    oppPlace = i;
                }
            }
            //figures out what index in the array was played by  you
            //this could be made much more efficient with a hashmap

            for (int i = 0; i < u.length; i++) {
                if (u[i].equals(you)) {
                    youPlace = i;
                }
            }
            int total = 0;
            //figures out whether a win occured, a tie, or a draw
            if ((opp.equals("A") && you.equals("Y")) || (opp.equals("B") && you.equals("Z")) || opp.equals("C") && you.equals("X")) {
                //win
                total = 6;
            } else if (oppPlace == youPlace) {
                //draw
                total = 3;
            } else {
                //lost
                total = 0;
            }
            //adds points based on what was played by you
            if (youPlace == 0) {
                total += 1;
            } else if (youPlace == 1) {
                total += 2;
            } else if (youPlace == 2) {
                total += 3;
            }
            totals.add(total);
        }
        int total = 0;
        //goes through the totals for each game and sums it into one int variable
        for (int i = 0; i < totals.size(); i++) {
            total += totals.get(i);
        }
      return String.valueOf(total);
    }
    @Override
    public String p2() throws IOException {
        //gets the inputs for day 2
        GetInputs INPUT2 = new GetInputs(22,2);
        //assigns the plays that are allowed by the opponent and the player into two arrays
        String[] opponent = {"A", "B", "C"};
        String[] u = {"X", "Y", "Z"};
        ArrayList<Integer> totals = new ArrayList<Integer>();
        //iterates through each line (aka each game)
        while (INPUT2.hasLines()) {
            //takes in the play made by the opp and you
            String opp = INPUT2.next();
            String u1 = INPUT2.next();
            String you = "";
            //this huge if statement's first branch figures out whether there is a rock, paper, scissors played by you
            //and this first branch tells us if we need to win or tie or lose
            if (u1.equals("X")) {
                //once we know what we need to do, we then will figure out what is required to be played by us
                //in order to win, tie, or lose
                //these are the second order branches
                if (opp.equals("A")) {
                    you = "Z";
                } else if (opp.equals("B")) {
                    you = "X";
                } else {
                    you = "Y";
                }
            } else if (u1.equals("Y")) {
                int temp = 0;
                for (int i = 0; i < opponent.length; i++) {
                    if (opponent[i].equals(opp)) {
                        temp = i;
                    }
                    you = u[temp];
                }
            } else if (u1.equals("Z")) {
                if (opp.equals("A")) {
                    you = "Y";
                } else if (opp.equals("B")) {
                    you = "Z";
                } else {
                    you = "X";
                }
            }
            int oppPlace = 0;
            int youPlace = 0;
            //figures out where in the index the opponent's play is
            for (int i = 0; i < opponent.length; i++) {
                if (opponent[i].equals(opp)) {
                    oppPlace = i;
                }
            }
            //figures out where in the index our play is
            for (int i = 0; i < u.length; i++) {
                if (u[i].equals(you)) {
                    youPlace = i;
                }
            }
            int total = 0;
            //figures out how many points to start with based on whether a win occurred, or a tie, or a loss
            if ((opp.equals("A") && you.equals("Y")) || (opp.equals("B") && you.equals("Z")) || opp.equals("C") && you.equals("X")) {
                total = 6;
            } else if (oppPlace == youPlace) {
                total = 3;
            } else {
                total = 0;
            }
            //adds points based on what we played
            if (youPlace == 0) {
                total += 1;
            } else if (youPlace == 1) {
                total += 2;
            } else if (youPlace == 2) {
                total += 3;
            }

            totals.add(total);
        }
        int total = 0;
        for (int i = 0; i < totals.size(); i++) {
            total += totals.get(i);
        }
        //returns the total points of all the games played
        return String.valueOf(total);
    }
    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC2());
    }
}
