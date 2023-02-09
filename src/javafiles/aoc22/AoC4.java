package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;

public class AoC4 implements DAYID {

    public String p1() throws FileNotFoundException {
        //takes in the input for day 4
        GetInputs INPUT1 = new GetInputs(22,4);
        int total = 0;
        //iterates through each line
        while (INPUT1.hasLines()) {
            //takes in each line
            String x = INPUT1.nextLine();
            int firstNum = 0;
            int secNum = 0;
            int thirNum = 0;
            int fourNum = 0;
            int counter = 0;
            int counter2 = 0;
            //finds the first num through a loop
            //could be made much more efficient if I had used .split() command
            for (int i = 0; i < x.length(); i++) {
                if (x.substring(i, i + 1).equals("-")) {
                    counter = i;
                    firstNum = Integer.parseInt(x.substring(0, i));
                    break;
                }
            }
            //finds the second num through a loop
            for (int i = 0; i < x.length(); i++) {
                if (x.substring(i, i + 1).equals(",")) {
                    secNum = Integer.parseInt(x.substring(counter + 1, i));
                    counter2 = i;
                    break;
                }
            }
            //finds the third num through a loop
            for (int i = counter2; i < x.length(); i++) {
                if (x.substring(i, i + 1).equals("-")) {
                    counter = i;
                    thirNum = Integer.parseInt(x.substring(counter2 + 1, i));
                    break;
                }
            }
            //simply uses the last index in order to find the last num
            fourNum = Integer.parseInt(x.substring(counter + 1));
            //finds whether the are fully contained
            if ((firstNum >= thirNum && secNum <= fourNum) || (thirNum >= firstNum && fourNum <= secNum)) {
                total++;
            }
        }
        //returns the total
        return String.valueOf(total);
    }

    public String p2() throws FileNotFoundException {
        //gets the input for day 4
        GetInputs INPUT2 = new GetInputs(22,4);
        int total = 0;
        //iterates through the lines
        while (INPUT2.hasLines()) {
            //takes on the value of each line
            String x = INPUT2.nextLine();
            int firstNum = 0;
            int secNum = 0;
            int thirNum = 0;
            int fourNum = 0;
            int counter = 0;
            int counter2 = 0;
            //finds first num
            for (int i = 0; i < x.length(); i++) {
                if (x.substring(i, i + 1).equals("-")) {
                    counter = i;
                    firstNum = Integer.parseInt(x.substring(0, i));
                    break;
                }
            }
            //finds second num
            for (int i = 0; i < x.length(); i++) {
                if (x.substring(i, i + 1).equals(",")) {
                    secNum = Integer.parseInt(x.substring(counter + 1, i));
                    counter2 = i;
                    break;
                }
            }
            //finds third num
            for (int i = counter2; i < x.length(); i++) {
                if (x.substring(i, i + 1).equals("-")) {
                    counter = i;
                    thirNum = Integer.parseInt(x.substring(counter2 + 1, i));
                    break;
                }

            }
            //find fourth num
            fourNum = Integer.parseInt(x.substring(counter + 1));
            //finds whether they are slightly covered by finding the inverse
            //as if they aren't completely to the left or the right of each other then they must be covering each other
            //even if only by a slight bit
            if (!((firstNum < thirNum && secNum < thirNum) || (firstNum > fourNum && secNum > fourNum))) {
                total++;
            }
        }
        //returns the summation of the total
        return String.valueOf(total);
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC4());
    }
}
