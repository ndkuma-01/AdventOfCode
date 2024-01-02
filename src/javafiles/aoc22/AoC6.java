package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public class AoC6 implements DAYID {
    @Override
    public String p1() throws IOException {
        //takes in the input for day 6
        GetInputs INPUT = new GetInputs(22,6);
        //takes in the main line
        String line = INPUT.nextLine();
        int counter =0 ;
        int howManyChecks = 4;
        //iterates till a break or a return occurs
        while(true){
            //takes in however many characters need to be checked at a time
            //and iterates this string up by 1 every time
            String s = line.substring(counter, counter+howManyChecks);
            //makes a hashset of unique characters
            HashSet<Character> set = new HashSet<Character>();
            for(int i =0 ; i < s.length(); i++){
                set.add(s.charAt(i));
            }
            //if the size of unique characters is equal to four then we have found the four unique characters
            if(set.size() == howManyChecks){
                //returns the index of the unique 4 characters
                return String.valueOf(counter + howManyChecks);
            }
            counter++;
        }
    }
    @Override
    public String p2() throws IOException {
        //the inputs are taken in for day 6
        GetInputs INPUT = new GetInputs(22,6);
        //the main line is taken in
        String line = INPUT.nextLine();
        //the same thing occurs as happened in the first part except this time the number of unique characters required is
        //14
        int counter =0 ;
        int howManyChecks = 14;
        while(true){
            String s = line.substring(counter, counter+howManyChecks);
            HashSet<Character> set = new HashSet<Character>();
            for(int i =0 ; i < s.length(); i++){
                set.add(s.charAt(i));
            }
            if(set.size() == howManyChecks){
                return String.valueOf(counter + howManyChecks);
            }
            counter++;
        }

    }
    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC6());
    }
}
