package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.Packets;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class AoC13 implements DAYID {
    //this solution makes use of a custom data type called packets
    //it is a very simple class that is only used to keep track of data,
    //to learn more about what it holds then look towards the Utilities.aoc22.Packets class.

    public Packets getPacket(String s) {
        //creates a packet for the packet we are trying to find that has that string
        Packets pack = new Packets();
        int i = 1;
        //goes for the entirety of the string
        while (i < s.length()) {
            //does a branched search keeping track of the depth and the end index
            if (s.charAt(i) == '[') {
                int depth = 1;
                int endI = i + 1;
                //when traversing through it reduces the depth when having encountered a ']'
                while (depth > 0) {
                    if (s.charAt(endI) == ']') {
                        depth--;
                    } else if (s.charAt(endI) == '[') {
                        //and it increases the depth whenever we have entered a new packet
                        depth++;
                    }
                    //adds to the end index for every depth search we do
                    endI++;
                }
                //we then recursively go through a smaller string to get the pack's children
                pack.children.add(getPacket(s.substring(i, endI)));
                //moves the index forward in order to narrow the search for the packet
                i = endI;
            } else {
                //it finds that it starts inside of a packet
                int endI = s.indexOf(",",i+1);
                if(endI == -1){
                    endI = s.indexOf("]",i);
                }
                //takes in the parsed number and tries to add it to the children of the main packet that we are finding
                String parsedNum = s.substring(i,endI);
                Packets parseNum = new Packets();
                //this try catch block is made just in case the packet is empty
                //if so then we don't care about it, so we don't needto do anything
                try{parseNum.value=Integer.parseInt(parsedNum);pack.children.add(parseNum);}catch(NumberFormatException e){}
                i = endI;
            }
            i++;
            }
        //returns the packets that we were looking for
        return pack;
        }

    public static int compare(Packets left, Packets right) {
        //this comparison method is a recursion based method of comparing two different packets with each other
        int compareIndex = 0;
        //this will go for the entirety of the smallest number of children from either the left or the right
        while(compareIndex < left.children.size() || compareIndex < right.children.size()) {
            //this handles if either the left or the right run out of children
            if(left.children.size() <= compareIndex)
                return 1;
            if(right.children.size() <= compareIndex)
                return -1;
            //takes in the children that is currently being compared and embodies them into two seperate packets
            Packets leftCur = left.children.get(compareIndex);
            Packets rightCur = right.children.get(compareIndex);

            //this is responsible for handling the case where we are dealing with a list on the left
            //and on the right we only have a single integer
            if(leftCur.children.size() > 0 && rightCur.value != -1) {
                Packets alpha = new Packets();
                Packets beta = new Packets();
                beta.value = rightCur.value;
                alpha.children.add(beta);
                //will recursively figure out the comparison between the children and the current left
                int compare = compare(leftCur,alpha);
                //this will return if the result was definitive,
                //if the result isn't definitive then we don't need to over the rest of the parents list
                //so because of that we can go ahead and continue, and also increase the comparison index
                if(compare != 0)
                    return compare;
                else {
                    compareIndex++;
                    continue;
                }
            }

            //this handles the opposite case from before where here we have an integer on the left hand side
            //and on the right hand side we are dealing with a list of children
            if(leftCur.value != -1 && rightCur.children.size() > 0) {
                Packets alpha = new Packets();
                Packets beta = new Packets();
                beta.value = leftCur.value;
                alpha.children.add(beta);
                //will recursively figure out the comparison between the children and the current right
                int compare = compare(alpha,rightCur);
                //this will return if the result was definitive,
                //if the result isn't definitive then we don't need to over the rest of the parents list
                //so because of that we can go ahead and continue, and also increase the comparison index
                if(compare != 0)
                    return compare;
                else {
                    compareIndex++;
                    continue;
                }
            }

            //parses in the values that are found from the children
            int leftVal = left.children.get(compareIndex).value;
            int rightVal = right.children.get(compareIndex).value;

            //if the values are both -1 than that means that they would be both lists rather than just a single value
            //because of this we then need to traverse throguh their children and compare those

            if(leftVal == -1 && rightVal == -1) {
                //this will go through each of the children recursively and go until they finally find values
                //only care about definitive result
                int compare = compare(left.children.get(compareIndex),right.children.get(compareIndex));
                if(compare != 0)
                    return compare;
                else {
                    compareIndex++;
                    continue;
                }
            }

            //once we reach this point we have gone through each of the edge cases and have solved them
            //as such we can now just do a simple integer comparison in order to compare them
            if(leftVal < rightVal)
                return 1;
            if(leftVal > rightVal)
                return -1;

            //if the left val and the right val are equal however then we must move over to the next index
            compareIndex++;
        }
        return 0;
    }
        @Override
        public String p1 () throws FileNotFoundException {
            //takes in the input for day 13
            GetInputs input = new GetInputs(22,13);
            //takes in the lines
            ArrayList<String> lines = new ArrayList<String>();
            //goes through each of the liens
            while (input.hasLines()) {
                //and adds them, ensuring to add a \n token so we can later .split() it by \n
                String result = "";
                for (int i = 0; i < 2; i++) {
                    if (i != 1) {
                        result += input.nextLine().concat("\n");
                    } else {
                        result += input.nextLine();
                    }
                }
                //adds this pack to the lines
                lines.add(result);
                //if the there is another line right after (the blank line that is found after every packet duo)
                //then we will go ahead and take the blank line away
                //however on the last packet duo there is no extra blank line, hence why this if statement is here
                if (input.hasLines()) {
                    String temp = input.nextLine();
                }
            }
            int ans = 0;

            for (int i = 0; i < lines.size(); i++) {
                //splits the packet duo into the left and right packets
                String s = lines.get(i);
                String left = s.split("\n")[0];
                String right = s.split("\n")[1];
                //gets the packet of the left and the right and ensures it is allocated to a custom made data type for packets
                Packets leftP = getPacket(left);
                Packets rightP = getPacket(right);

                //compares the left and the right and if they are the same then it will increment the answer by i + 1
                if(compare(leftP, rightP) == 1){
                    ans+= i+1;
                }
            }
            //returns the answer
            return String.valueOf(ans);
    }

        @Override
        public String p2 () throws FileNotFoundException {
        //takes in the input for day 13
        GetInputs input = new GetInputs(22,13);
        //seperates the input into lines
            String[] lines = input.filetoArray();
            ArrayList<Packets> sorted = new ArrayList<Packets>();
            //takes in each individual line
            for(String s : lines){
                //if the line is empty then we skip
                if(s.equals("")){continue;}
                //gets the packet and adds it to the sorted packets
                Packets p = getPacket(s);
                sorted.add(p);
            }
            //finds the divider packets that are specified by the question
            Packets divider2 = getPacket("[[2]]");
            Packets divider6 = getPacket("[[6]]");
            //adds them to sorted
            sorted.add(divider2);
            sorted.add(divider6);
            //the sorted are then sorted based on the comparison method declared earlier
            sorted.sort(AoC13::compare);
            Collections.reverse(sorted);

            int ans = (sorted.indexOf(divider6) + 1)*(sorted.indexOf(divider2) + 1 );
            return String.valueOf(ans);
    }

        public static void main (String[]args) throws FileNotFoundException {
            RUNDAY.run(new AoC13());
        }

    }
