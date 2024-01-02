package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AoC10 implements DAYID {

    @Override
    public String p1() throws IOException {
        GetInputs input = new GetInputs(22,10);
        ArrayList<String> lines =input.filetoArrayList();
        int x = 1;
        int placeholder = 0;

        int answer = 0;
        //keeps an arraylist to compare too later on, this holds the cycles that we want to keep track of
        ArrayList<Integer> interesting = new ArrayList<Integer>();
        interesting.add(20);
        interesting.add(60);
        interesting.add(100);
        interesting.add(140);
        interesting.add(180);
        interesting.add(220);
        //goes through each of the lines
        for (int i = 0; i < lines.size(); i++) {
            //splits the lines into parts
            String[] parts = lines.get(i).split(" ");
            if (parts[0].equals("noop")) {
                //if noop, nothing happens
                placeholder += 1;

                if (interesting.contains(placeholder)) {
                    //if the placeholder is in the cycles we want to keep track of
                    //it adds it to our answer
                    answer += placeholder * x;
                }

            } else if (parts[0].equals("addx")) {
                //if there is an addx then it takes in how much is added
                int addx = Integer.parseInt(parts[1]);
                //manipulates the x values
                x += addx;
                placeholder++;
                //increases the placeholder
                if (interesting.contains(placeholder)) {
                    //checks if it is held in the interesting cycles
                    answer += placeholder * (x - addx);
                }
                placeholder++;
                if (interesting.contains(placeholder)) {
                    answer += placeholder * (x - addx);
                }

            }

        }
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws IOException {
        GetInputs input = new GetInputs(22,10);
        ArrayList<String> lines = input.filetoArrayList();
        int x_val = 1;
        int placeholder = 0;
        //creates a bit array for holding in where the CRT parser is
        int[] data = new int[241];
        for (int i = 0; i < data.length; i++) {
            data[i] = 1;
        }
        //goes through each line
        for (int i = 0; i < lines.size(); i++) {
            //takes all the parts of each line and puts them into an array
            String[] parts = lines.get(i).split(" ");
            //does the same thing as part two, but the x val is instead added to the bit array
            if (parts[0].equals("noop")) {
                placeholder++;
                data[placeholder] = x_val;


            } else if (parts[0].equals("addx")) {
                //if an addx is found then it will inrceease the placeholder value (simulating one cycle of the crt) and allocate
                //the current x value
                //and then adds the addx value
                int addx = Integer.parseInt(parts[1]);
                data[placeholder + 1] = x_val;
                //then adds the addx
                x_val += addx;
                //simulates two cycles
                placeholder += 2;
                //at this cycle the new x value is actually inputted
                data[placeholder] = x_val;
            }
        }
        //creates a string 2d array to hold the answer
        String[][] ans = new String[6][40];
        //goes through and calulates whether it needs to be filled in or not
        for (int row = 0; row < ans.length; row++) {
            for (int col = 0; col < ans[0].length; col++) {
                int counter =  col + 1 * (row * 40);
                if (Math.abs(data[counter] - col) <= 1) {
                    //uses double thick lines in order for easier readability
                    ans[row][col] = "##";
                } else {
                    ans[row][col] = "  ";
                }
            }
        }
        //prints out the array that holds the characters
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                System.out.print(ans[i][j]);
            }
            System.out.println();
        }
    return "";
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC10());
    }
}
