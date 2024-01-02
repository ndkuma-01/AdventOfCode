package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class AoC14 implements DAYID {
    ArrayList<ArrayList<Character>> grid;



    @Override
    public String p1() throws IOException {
        grid = new GetInputs(23,14).getAsCharArrayList();
         roll();
        return String.valueOf(grabScore());
    }

    @Override
    public String p2() throws IOException {
        grid = new GetInputs(23,14).getAsCharArrayList();
        HashMap<String, Integer> r = new HashMap<>();
        for(int i = 1 ; i <= 1000000000L; i++){
            IntStream.range(0,4).forEach(x-> {roll();rotate();});
            String comp = compress();
            i+= (r.containsKey(comp))? ((1000000000L-i)/(i-r.get(comp))) * (i-r.get(comp)) : 0;
            r.put(comp, i);
        }
        return String.valueOf(grabScore());
    }
    public void roll(){
    for(int k = 0 ; k < grid.get(0).size(); k++) {
        for (int i = grid.size() - 1; i > 0; i--) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) == 'O' && grid.get(i - 1).get(j) == '.') {
                    grid.get(i).set(j, '.');
                    grid.get(i - 1).set(j, 'O');
                }
            }
        }
    }
    }

    public void rotate(){
        int m = grid.size();
        char[][] temp = new char[grid.size()][grid.get(0).size()];
        for(int i = 0 ; i < grid.size() ; i++){
            for(int j = 0 ; j < grid.get(i).size(); j++){
                temp[i][j] = grid.get(i).get(j);
            }
        }

        for(int i  = 0 ; i < temp.length ; i++){
            for(int j = 0  ; j < temp[i].length ;j++){
                temp[j][grid.size() - 1 - i] = grid.get(i).get(j);
            }
        }
        ArrayList<ArrayList<Character>> temp2 = new ArrayList<>();
        for(int i = 0 ; i < temp.length ; i++) {
            temp2.add(convertCharArrayToArrayList(temp[i]));
        }

        grid = temp2;
    }

    public String compress(){
        String res = "";
        for(int i = 0 ; i < grid.size(); i++){
            for(int j = 0 ; j < grid.get(i).size(); j++){
                res += grid.get(i).get(j);
            }
        }
        return res;
    }
    public static ArrayList<Character> convertCharArrayToArrayList(char[] charArray) {
        ArrayList<Character> charList = new ArrayList<>();

        for (char c : charArray) {
            charList.add(c);
        }

        return charList;
    }


    public Long grabScore(){
        Long score = 0L;
        for(int i = 0, scoreRow = grid.size(); i < grid.size() ; i++, scoreRow--  ){
            for(int j = 0 ;  j < grid.get(i).size() ;j++){
                if(grid.get(i).get(j) == 'O'){
                    score += scoreRow;
                }
            }

        }
        return score;
    }
    public void print(ArrayList<ArrayList<Character>> h){
        for(int i = 0 ; i < h.size(); i++){
            for(int j = 0 ; j < h.get(i).size() ;j++){
                System.out.print(h.get(i).get(j));
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC14());
    }
}
