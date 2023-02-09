package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class AoC25 implements DAYID {

    @Override
    public String p1() throws FileNotFoundException {
        //gets the input for day 25
        GetInputs input = new GetInputs(22,25);
        ArrayList<String> lines = new ArrayList<String>();
        //simply takes the lines and add them to the input
        //this could have been made simpler by using the GetInputs methods
        //however GetInputs, DAYID, RUNDAY, and more were implemented after this was coded
        while(input.hasLines()){lines.add(input.nextLine());}
        //we assosciate SNAFU values to DECIMAL ones and vice versa
        HashMap<String, Integer> SNAFUTODECIMAL = new HashMap<String,Integer>();
        SNAFUTODECIMAL.put("0",0);
        SNAFUTODECIMAL.put("1",1);
        SNAFUTODECIMAL.put("2",2);
        SNAFUTODECIMAL.put("-",-1);
        SNAFUTODECIMAL.put("=",-2);
        HashMap<Integer, String> DECIMALTOSNAFU = new HashMap<Integer,String>();
        DECIMALTOSNAFU.put(0,"0");
        DECIMALTOSNAFU.put(1,"1");
        DECIMALTOSNAFU.put(2,"2");
        DECIMALTOSNAFU.put(-1,"-");
        DECIMALTOSNAFU.put(-2,"=");
        long answer =0 ;
        //we go through each of the lines
        for(int i =0 ; i < lines.size(); i++){
            long ans = 0;
            long start = 1;
            //we first reverse the line in order to make it easier to raise to the powers
            String line = reverse(lines.get(i));
            for(int j = 0 ; j < line.length(); j++){
                ans += (long) start * SNAFUTODECIMAL.get(line.substring(j,j+1));
                start *=5;
            }
            answer += ans;
        }
        String answerInSNAFU = "";
        while(answer>0){
            //we then convert back to snafu
            int d = (int) ((answer + 2) % 5 ) - 2;
            answerInSNAFU += DECIMALTOSNAFU.get( d );
            answer -=  d;
            answer /= 5;
        }
        //we print out the reverse value
        return reverse(answerInSNAFU);
    }

    @Override
    public String p2() throws FileNotFoundException {
        return "NO PART 2";
    }

    public static String reverse(String a) {
        // a simple string reverse method
        String result = "";
        for (int i = a.length(); i > 0; i--) {
            result += a.substring(i - 1, i);
        }
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC25());
    }
}
