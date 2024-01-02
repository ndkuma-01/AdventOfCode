package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.Monkey;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AoC11 implements DAYID {
    public int getGCD(int x , int y){
        // a recursive way to get the GCD of two numbers
        return y == 0 ? x : getGCD(y, x%y);
    }
    public int getLCM(List<Integer> a, int b){
        //takes in a list of numbers and gets the lcm of each part of the list
        //a recursive application of getting the lcm
        if(a.size() - 1 == b){return a.get(b);}
        int x = a.get(b);
        int y = getLCM(a,b+1);
        return (x*y)/getGCD(x,y);

    }
    @Override
    public String p1() throws IOException {
        //first takes in the input for day 11
        GetInputs input = new GetInputs(22,11);
        //creates a list of a custom made data type called monkeys
        //to learn more about the monkeys data type navigate to Utilities.aoc22.Monkey in order to view the monkey class
        ArrayList< Monkey > monkeys = new ArrayList<>();
        //goes through the eight monkeys
        for(int i = 0 ; i < 8; i++){
            //takes in the input
            //the purpose of the if statement is that the first branch expects 7 lines to be taken one
            //one of the 7 lines is an empty line
            //however on the last monkey there is only 6 lines, and that last empty line is nonexistent
            //so to avoid an error the if statement exists
            String[] lines = new String[7];
            if(i < 7){
                for(int j = 0 ; j < 7; j++){
                    lines[j] = input.nextLine().trim();
                }
            }else if(i < 8){
                for(int j = 0 ; j < 6; j++){
                    lines[j] = input.nextLine().trim();
                }
            }
            //makes a temporary monkey that is modeled after each of the 8 monkeys, and will be added to the arraylist
            //of monkeys
            Monkey temp = new Monkey();
            //splits the monkeys items and allocates them to an array list
            String[] line1 = lines[1].split(", | +");
            ArrayList<Long> itemsHeld = new ArrayList<>();
            for(int j = 2; j < line1.length; j++){
                itemsHeld.add(Long.parseLong(line1[j]));
            }
            //adds the items held to the temp monkeys attribute of itemsheld
            temp.items= itemsHeld;
            //looks at the second line and finds out what operator is occuring by altering some boolean values
            //in temp's attributes
            String[] line2 = lines[2].split(" ");
            temp.add = line2[line2.length - 2].equals("+");
            if((line2[line2.length - 1].equals("old"))){
                temp.square =  true;
            }else{
                temp.operationNum = Integer.parseInt(line2[line2.length - 1]);
            }
            //takes in the divisible by number
            String[] line3 = lines[3].split(" ");
            temp.modNum = Integer.parseInt(line3[line3.length - 1]);
            //takes in the monkey that the item should be thrown too if a true number occurs
            String[] line4 = lines[4].split(" ");
            temp.trueMonk = Integer.parseInt(line4[line4.length-1]);
            //takes in the monkey that the item should be thrown too if a false number occurs
            String[] line5 = lines[5].split(" ");
            temp.falseMonk = Integer.parseInt(line5[line5.length - 1]);
            monkeys.add(temp);
        }
        //creates an int array that keeps track of how many times each monkeys inspects an item
        int[] cnt = new int[monkeys.size()];
        //goes through 20 rounds
        for(int i = 0 ; i < 20; i++){
            //goes through each of the monkeys
            for(int j = 0 ; j < monkeys.size(); j++){
                //gets the monkey we are looks at
                Monkey temp = monkeys.get(j);
                //if there are no items it will skip this monkey
                if(temp.items.size() <1){continue;}
                //goes through all of the items held by the monkey
                while(temp.items.size()>0){
                    //adds to the inspection counter
                    cnt[j]++;
                    //takes the current item and identifies the worrylevel it has
                    long worrLevel = temp.items.remove(0);
                    //checks what operation needs to occur
                    if(temp.square){
                        worrLevel*=worrLevel;
                    }else if(temp.add){
                        worrLevel+=temp.operationNum;
                    }else{
                        worrLevel*=temp.operationNum;
                    }
                    //divides the worry level by 3 simulating when the monkey gets bored
                    worrLevel/=3;
                    //checks whether the false or the true monkey get the item
                    if(worrLevel%temp.modNum==0)
                        monkeys.get(temp.trueMonk).items.add(worrLevel);
                    else
                        monkeys.get(temp.falseMonk).items.add(worrLevel);

                }
            }
        }
        //takes in the inspection count array and sorts them by smallest to greatest
        Arrays.sort(cnt);
        //takes the top two biggest ones and multiplies them for the answer
        int answer = cnt[cnt.length -2] * cnt[cnt.length -1];
        return String.valueOf(answer);
    }
    @Override
    public String p2() throws IOException {
        //takes in the input for day 11
        GetInputs input = new GetInputs(22,11);
        //creates an array list of a custom data type called monkeys
        //to learn more about the monkeys data type navigate to Utilities.aoc22.Monkey in order to view the monkey class
        ArrayList< Monkey > monkeys = new ArrayList<>();
        //goes through the 8 monkeys
        for(int i = 0 ; i < 8; i++){
            //takes in all the lines
            String[] lines = new String[7];
            if(i < 7){
                for(int j = 0 ; j < 7; j++){
                    lines[j] = input.nextLine().trim();
                }
            }else if(i < 8){
                for(int j = 0 ; j < 6; j++){
                    lines[j] = input.nextLine().trim();
                }
            }
            //creates a temporary monkey that will be modeled after each monkey and then added to the monkeys arraylist
            Monkey temp = new Monkey();
            //takes in the worry levels of the monkey
            String[] line1 = lines[1].split(", | +");
            //creates an array list of longs to hold in all the items that are being held
            ArrayList<Long> itemsHeld = new ArrayList<>();
            //goes through and adds all the items to the itemsHeld array list
            for(int j = 2; j < line1.length; j++){
                itemsHeld.add(Long.parseLong(line1[j]));
            }
            temp.items= itemsHeld;
            //takes in the second line
            String[] line2 = lines[2].split(" ");
            //figures out what operation will occur
            temp.add = line2[line2.length - 2].equals("+");
            if((line2[line2.length - 1].equals("old"))){
                temp.square =  true;
            }else{
                temp.operationNum = Integer.parseInt(line2[line2.length - 1]);
            }
            //takes in the third line
            String[] line3 = lines[3].split(" ");
            //takes in the number that must be tested divisibility against
            temp.modNum = Integer.parseInt(line3[line3.length - 1]);
            //takes in the fourth line
            String[] line4 = lines[4].split(" ");
            //finds the true monkey
            temp.trueMonk = Integer.parseInt(line4[line4.length-1]);
            //finds the false monkey
            String[] line5 = lines[5].split(" ");
            temp.falseMonk = Integer.parseInt(line5[line5.length - 1]);
            //adds this monkey and all of its attributes to the arraylist of all the monkeys
            monkeys.add(temp);
        }
        //this is where a clever method is used in order to keep the size of the numbers down allowing for us too use
        //data types like longs and allow for quicker execution
        //first an inspectioncount array is made
        long[] cnt = new long[monkeys.size()];
        //here we make a list of all of the divisible by numbers for each monkey and makes them into a list
        List<Integer> temp = monkeys.stream().map(x -> x.modNum).toList();
        //the lcm is found between all of the numbers
        int smallest = getLCM(temp, 0);
        //through this why by finding the main factor, at the end of each calulation it can be modded against the factor
        //and still preserve its divisibility between all of the mod nums
        //goes through 10,000 rounds
        for(int i = 0; i < 10_000; i++){
            //goes through each of the monkeys
            for(int j = 0; j < monkeys.size(); j++){
                //takes in the current monkey that we are looking at
                Monkey currentMonkey = monkeys.get(j);
                //if there are no items it will skip this monkey
                if(0==currentMonkey.items.size()){continue;}
                //it will iterate through all of the items currently being held by the monkey
                while(currentMonkey.items.size() > 0){
                    //adds to the inspection count
                    cnt[j]++;
                    //does the operation
                    long worrLevel = currentMonkey.items.remove(0);
                    if(currentMonkey.square){
                        worrLevel*=worrLevel;
                    }else if(currentMonkey.add){
                        worrLevel+=currentMonkey.operationNum;
                    }else{
                        worrLevel*=currentMonkey.operationNum;
                    }
                    //mods by the factor which makes it both smaller while again still preserving the divisibility
                    worrLevel%=smallest;
                    if(0 == worrLevel%currentMonkey.modNum)
                        monkeys.get(currentMonkey.trueMonk).items.add(worrLevel);
                    else
                        monkeys.get(currentMonkey.falseMonk).items.add(worrLevel);
                }
            }
        }
        //sorts the array of inspections counted from smallest to greatest
        Arrays.sort(cnt);
        //finds the product of the top two insepction counts and returns it as the answer
        Long answer = cnt[cnt.length -2] * cnt[cnt.length -1];
        return String.valueOf(answer);
    }
    public static void main(String[] args)throws IOException {
        RUNDAY.run(new AoC11());
    }
}
