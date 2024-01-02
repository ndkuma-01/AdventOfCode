package javafiles.aoc15;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AoC1 extends RUNDAY implements DAYID {
    @Override
    public String p1() throws IOException {
        GetInputs input = new GetInputs(15,1);
        String instruc = input.nextLine();
        int answer= 0 ;
        for(int i = 0 ; i < instruc.length(); i++){
            if(instruc.substring(i,i+1).equals(")")){
                answer--;
            }else{
                answer++;
            }
        }
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws IOException {
        GetInputs input = new GetInputs(15,1);
        String instruc = input.nextLine();
        int answer= 0 ;
        for(int i = 0 ; i < instruc.length(); i++){
            if(instruc.substring(i,i+1).equals(")")){
                answer--;
            }else{
                answer++;
            }
            if(answer < 0){
                answer = i + 1;
                break;
            }
        }
        return String.valueOf(answer);
    }


    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC1());
    }
}

