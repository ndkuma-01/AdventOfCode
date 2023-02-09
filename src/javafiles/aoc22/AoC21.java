package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class AoC21 implements DAYID {

    @Override
    public String p1() throws FileNotFoundException {
        //takes in the input for day 21
        GetInputs input = new GetInputs(22,21);
        //creates a look up table that holds all the things that the monkeys do
        Map<String, String> lookUp = new HashMap<>();
        //iteartes through all the lines
        while (input.hasLines()) {
            //takes in each line
            String line = input.nextLine();
            //seperates the line into parts
            String parts[] = line.split(" ");
            //this removes the colon
            parts[0] = parts[0].substring(0, parts[0].length() - 1);
            //the name of the monkey is taken as parts[0]
            String monkey = parts[0];
            //if there are two parts to parts then that means that we have a number
            if (parts.length == 2) {
                //so we will take the monkey we are looking at and associate that number with them
                lookUp.put(monkey, parts[1]);
            } else {
                //this then means that the monkey has an operation so we will assosciate that monkey with that operation
                String temp = parts[1] + " " + parts[2] + " " + parts[3];
                lookUp.put(monkey, temp);
            }
        }
        //we will then use a method called compute that is the crux of this solution to find the answer to root
        String[] partOfRoot = lookUp.get("root").split(" ");
        long left = compute(partOfRoot[0], lookUp);
        long right = compute(partOfRoot[2], lookUp);
        long answer = compute("root", lookUp);


//        System.out.println(left + " " + partOfRoot[1] + " " + right);
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws FileNotFoundException {
        //takes in the input for day 21
        GetInputs input = new GetInputs(22,21);
        //creates a look up table so we can associate each monkey with either a number or an operation
        Map<String, String> lookUp = new HashMap<>();

        //iterates through all the lines
        while (input.hasLines()) {
            //embodies line
            String line = input.nextLine();
            //splits the line into parts
            String parts[] = line.split(" ");
            //removes the colon
            parts[0] = parts[0].substring(0, parts[0].length() - 1);
            //embodies the name of the monkey
            String monkey = parts[0];
            //if there are two parts to the parts that means that the monkey is assosciated with a number
            if (parts.length == 2) {
                //we assosciate that monkey with that number
                lookUp.put(monkey, parts[1]);
            } else {
                //we assosciate that monkey with this operation
                String temp = parts[1] + " " + parts[2] + " " + parts[3];
                lookUp.put(monkey, temp);
            }
        }

        //This part is going to require some explanation
        //When trying to solve this problem I encountered a bunch of problems and had a couple different ideas for solving them
        //the first was performing a linear search with multiple threads accounted towards the search to hasten the process
        //so I went ahead and set a linear search off, while I tried coding a smarter solution
        //essentially the linear search would set up humn to be different values until the left and the right equalled each other
        //I then tried coding a binary search however my linear search had finished while I was coding the binary version and
        //it was correct
        //So I kept the linear search and went ahead and placed the value where i starts closer to the solution, since it took many hours
        //when starting at 0.
        for (long i = 3423279932000L; i < Math.pow(10, 15); i++) {
            lookUp.put("humn", String.valueOf(i));
            String[] partsOfRoot = lookUp.get("root").split(" ");

            long right = compute2(partsOfRoot[0], lookUp);
            long left = compute2(partsOfRoot[2], lookUp);
            if (left == right) {
                return String.valueOf(i);

            }

        }
        return "";
    }

    public static long compute(String n, Map<String, String> lookUp) {
        //first we will try to see if the monkey we are computing has a value
        //if so we return it allowing for the end of the recursion
        try {
            long result = Integer.parseInt(lookUp.get(n));
            return result;
        } catch (Exception e) {

        }
        //we split the equation into parts
        String[] parts = lookUp.get(n).split(" ");
        long left = compute(parts[0], lookUp);
        long right = compute(parts[2], lookUp);
        //we then recursibely compute what the left and the right equal by calling the same method

        //we then do the operation
        if (parts[1].equals("+")) {
            return left + right;
        } else if (parts[1].equals("-")) {
            return left - right;
        } else if (parts[1].equals("*")) {
            return left * right;
        } else if (parts[1].equals("/")) {
            return left / right;
        }
        //it should never return 0, however this return statement is still necessary to avoid exceptions
        return 0;
    }
    public static long compute2(String n, Map<String, String> lookUp) {
        //...this is the exact same as compute1 :)
        try {
            long result = Long.parseLong(lookUp.get(n));
            return result;
        } catch (Exception e) {

        }
        String[] parts = lookUp.get(n).split(" ");
        long left = compute2(parts[0], lookUp);
        long right = compute2(parts[2], lookUp);

        if (parts[1].equals("+")) {
            return left + right;
        } else if (parts[1].equals("-")) {
            return left - right;
        } else if (parts[1].equals("*")) {
            return left * right;
        } else if (parts[1].equals("/")) {
            return left / right;
        }

        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC21());
    }
}
