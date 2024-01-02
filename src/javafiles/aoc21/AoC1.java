package javafiles.aoc21;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AoC1 implements DAYID {




    @Override
    public String p1() throws IOException {
        GetInputs input = new GetInputs(21,1);
        ArrayList<Integer> sweeps = new ArrayList<Integer>();
        while(input.hasLines()){
            sweeps.add(Integer.parseInt(input.nextLine()));
        }
        int answer = 0;
        for(int i = 1 ;i < sweeps.size(); i++){
            if(sweeps.get(i-1) <sweeps.get(i)){
                answer++;
            }
        }
        System.out.println(sweeps.size());
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws IOException {
        GetInputs input = new GetInputs(21, 1);
        ArrayList<Integer> sweeps = new ArrayList<Integer>();
        while (input.hasLines()) {
            sweeps.add(Integer.parseInt(input.nextLine()));
        }
        ArrayList<Integer> windows = new ArrayList<Integer>();
        for (int i = 0; i < sweeps.size() - 2; i ++) {
            int add = (sweeps.get(i) + sweeps.get(i + 1) + sweeps.get(i + 2));
            windows.add(add);
        }
        int answer = 0;
        for(int i = 1 ;i < windows.size(); i++){
            if(windows.get(i-1) < windows.get(i)){
                answer++;
            }
        }
        return String.valueOf(answer);
    }
    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC1());
    }
}
