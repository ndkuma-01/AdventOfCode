package javafiles.aoc15;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class AoC5 extends RUNDAY implements DAYID {
    @Override
    public String p1() throws IOException {
        int nice = 0;
        GetInputs input = new GetInputs(15, 5);
        while (input.hasLines()) {
            String line = input.nextLine();
            boolean vowels = false;
            boolean nope = false;
            boolean dubs = false;
            int vow = 0;
            for(int i = 0 ; i < line.length(); i++){
                HashSet<String> vows = new HashSet<String>(Arrays.asList("a", "e", "i", "o", "u"));
                vows.add(line.substring(i,i+1));
                if(vows.size() == 5){
                    vow++;
                }

                if(vow >= 3){
                    vowels = true;
                    break;
                }
            }
            for(int i = 0 ;i < line.length() - 1; i++){
                HashSet<String> nonos = new HashSet<String>(Arrays.asList("ab","cd","pq","xy"));
                nonos.add(line.substring(i,i+2));
                if(nonos.size() == 4){
                    nope = true;
                    break;
                }
            }
            for(int i = 0 ; i < line.length() - 1 ; i++){
                if(line.charAt(i) == line.charAt(i+1)){
                    dubs = true;
                    break;
                }
            }
            if(dubs && !nope && vowels){
                nice++;
            }
        }
        return String.valueOf(nice);
    }

    @Override
    public String p2() throws IOException {
        GetInputs input = new GetInputs(15,5);
        int nice = 0 ;
        while(input.hasLines()){
            String line = input.next();
            boolean pair = false;
            boolean sandwich = false;

            for(int i = 0 ; i < line.length() - 2; i++){
                String check = line.substring(i,i+2);
                for(int j = i + 2; j < line.length() - 1 ; j++){
                    if(check.equals(line.substring(j,j+2))){
                        pair = true;
                        break;
                    }
                }
                if(pair){
                    break;
                }
            }
            for(int i = 0 ; i < line.length() - 2; i++){
                if(line.charAt(i) == line.charAt(i+2)){
                    sandwich = true;
                    break;
                }
            }
            if(sandwich && pair){
                nice++;
            }
        }
        return String.valueOf(nice);
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC5());
    }
}
