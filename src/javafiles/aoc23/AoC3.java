package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;
import java.util.HashMap;


public class AoC3 implements DAYID {
    int[] coordTransform = {-1,0,1};

    @Override
    public String p1() throws FileNotFoundException {
        GetInputs in = new GetInputs(23,3);
        Character[][] map = new Character[140][140];
        int cnt = 0;
        while(in.hasLines()) {
            String line = in.nextLine();
            for (int i = 0; i < line.length(); i++) {
                map[cnt][i] = line.charAt(i);
            }
            cnt++;
        }
        int result = 0;
        for(int i = 0; i < map.length; i++){
            boolean adjacentPart = false;
            int runNum = 0;
            for(int j = 0; j < map[i].length + 1; j++){
                if(j < map[i].length && Character.isDigit(map[i][j])){
                    runNum = runNum * 10 + Integer.parseInt(String.valueOf(map[i][j]));
                    for(int k : coordTransform){
                        for(int l  : coordTransform){
                            if((0<= i + k && i + k < map.length)&& 0 <= j + l && j+l < map[0].length){
                                char currChar = map[i +k][j+l];
                                if(!Character.isDigit(currChar) && currChar!= '.'){
                                    adjacentPart = true;
                                }
                            }
                        }
                    }
                }else if( runNum > 0){
                    if(adjacentPart){
                        result += runNum;
                    }
                    runNum = 0;
                    adjacentPart = false;
                }
            }
        }
        return String.valueOf(result);
    }


    @Override
    public String p2() throws FileNotFoundException {
        GetInputs in = new GetInputs(23,3);
        Character[][] map = new Character[140][140];
        HashMap<ArrayList<Integer>, ArrayList<Integer>> gearMap = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();
        int cnt = 0;
        while(in.hasLines()) {
            String line = in.nextLine();
            for (int i = 0; i < line.length(); i++) {
                map[cnt][i] = line.charAt(i);
            }
            cnt++;
        }
        int result = 0;
        for(int i = 0; i < map.length; i++){
            boolean adjacentPart = false;
            int runNum = 0;
            HashSet<ArrayList<Integer>> gears = new HashSet<ArrayList<Integer>>();
            for(int j = 0; j < map[i].length + 1; j++){
                if(j < map[i].length && Character.isDigit(map[i][j])){
                    runNum = runNum * 10 + Integer.parseInt(String.valueOf(map[i][j]));
                    for(int k : coordTransform){
                        for(int l  : coordTransform){
                            if((0<= i + k && i + k < map.length)&& 0 <= j + l && j+l < map[0].length){
                                char currChar = map[i +k][j+l];
                                if(currChar == '*'){
                                    ArrayList<Integer> temp = new ArrayList<>(List.of(i+k, j+l));
                                    gears.add(temp);
                                }

                            }
                        }
                    }
                }else if( runNum > 0){
//                    System.out.println(gearMap);
                    for(ArrayList<Integer> gear : gears){
                        ArrayList<Integer> temp = new ArrayList<>();


                        if(gearMap.get(gear) ==  null){
                            temp.add(runNum);
                            gearMap.put(gear, temp);
                        }else{
//                            System.out.println("ran");
                            temp = gearMap.get(gear);
                            temp.add(runNum);
                            gearMap.put(gear, temp);
                        }
                    }

                    runNum = 0;
                    gears.clear();
                }
            }
        }


        for(Map.Entry<ArrayList<Integer>, ArrayList<Integer>> entry : gearMap.entrySet()){
            ArrayList<Integer> key = entry.getKey();
            ArrayList<Integer> val = entry.getValue();
//            System.out.println(key);
            if(val.size() == 2){

                result += val.get(0) * val.get(1);
            }

        }
        return String.valueOf(result);
    }


    public static void main(String[] args) throws FileNotFoundException{
        RUNDAY.run(new AoC3());
    }
}
