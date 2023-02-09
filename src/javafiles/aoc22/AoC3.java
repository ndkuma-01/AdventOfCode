package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;

public class AoC3 implements DAYID {
    @Override
    public String p1() throws FileNotFoundException {
        //takes in the input for day 3
        GetInputs INPUT1 = new GetInputs(22,3);
        int total = 0;
        //iterates through all the lines
        while (INPUT1.hasLines()) {
            //takes in each line
            String x = INPUT1.next();
            //finds the first half and the second half of the line
            String firstHalf = x.substring(0, (x.length() / 2));
            String secondHalf = x.substring((x.length() / 2));
            //finds out what part of the string is the same
            String same = isSame(firstHalf, secondHalf);
            //figures out the summation of all the priority values of the same characters
            total += priority(same);

        }
        //returns the summation
        return String.valueOf(total);
    }
    @Override
    public String p2() throws FileNotFoundException {
        GetInputs INPUT2 = new GetInputs(22,3);
        int total = 0;
        //iterates through all the lines
        while(INPUT2.hasLines()){
            //takes in each three lines
            String x = INPUT2.nextLine();
            String y = INPUT2.nextLine();
            String z = INPUT2.nextLine();
            //uses a new version of the isSame method in order for it to find the same between three lines
            String same = isSame2(x,y,z);
            //summates the priority scores for the character shared between the three lines
            total += priority(same);
        }
        //returns the total
        return String.valueOf(total);
    }
    public static String isSame(String fH, String sH) {
        //used to find the character shared between the first half and the second half
        String result = "";
        //goes through each character of the first half...
        for (int i = 0; i < fH.length(); i++) {
            //and compares it to each character of the second half
            for (int j = 0; j < sH.length(); j++) {
                if (fH.charAt(i) == sH.charAt(j)) {
                    result = fH.substring(i, i + 1);
                }
            }
        }
        //returns what is the unique character
        return result;
    }
    public static String isSame2(String fH, String sH, String tH){
        //used to find the character shared between 3 lines
        String result = "";
        //goes through the first line
        for(int i = 0 ; i< fH.length(); i++){
            //and goes through the second line
            for(int j=0; j< sH.length(); j++){
                //and if it finds a value shared between the first two
                if(fH.charAt(i) == sH.charAt(j)){
                    //then it takes that value and checks the third
                    for(int k = 0 ; k < tH.length(); k++){
                        if(fH.charAt(i) == tH.charAt(k)){
                            result = fH.substring(i,i+1);
                            break;
                        }
                    }

                }
            }
        }
        return result;
    }
    public static int priority(String c) {
        //has an array for each letter, where its index is the priority score + 1
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        int result = 0;
        //goes through the array when lowercase
        if (c.toLowerCase().equals(c)) {
            for (int i = 0; i < letters.length; i++) {
                if (c.equals(letters[i])) {
                    result = i + 1;
                }
            }
        } else {
            //iterates through when its upper case and adds 27 for the respective uppercase ascii value
            for (int i = 0; i < letters.length; i++) {
                if (c.equals(letters[i].toUpperCase())) {
                    result = i + 27;
                }
            }
        }
        //returns the priority
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC3());
    }
}
