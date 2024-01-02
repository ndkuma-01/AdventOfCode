package javafiles.aoc21;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AoC3 implements DAYID {

    @Override
    public String p1() throws IOException {
        GetInputs input = new GetInputs(21,3);
        int onesCounter = 0;
        ArrayList<String> lines = new ArrayList<String>();
        while(input.hasLines()){
            lines.add(input.next());
        }
        ArrayList<Integer> places = new ArrayList<Integer>();
//        places.add(0);
//        places.add(0);
//        places.add(0);
//        places.add(0);
//        places.add(0);
//        places.add(0);
//        places.add(0);
        places.add(0);
        places.add(0);
        places.add(0);
        places.add(0);
        places.add(0);

        String gamma = "";
        String epsilon = "";
        for(int i = 0 ; i < lines.get(0).length(); i++){
            for(int j = 0; j < lines.size(); j++){
//                i, places.get(i)+ Integer.parseInt(lines.get(j).substring(i,i+1)));
            }

            if(places.get(i) > (places.size()/2) ){
                gamma = gamma + "1";
                epsilon = epsilon + "0";
            }else{
                gamma = gamma + "0";
                epsilon = epsilon + "1";

            }
        }
        System.out.println(places);

        return String.valueOf(BtoD(gamma) * BtoD(epsilon));
    }

    @Override
    public String p2() throws IOException {
        return null;
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC3());
    }

    public static int BtoD(String bin){
        int decimal = 0;
        int counter = 0 ;
        for(int i = bin.length(); i >0; i--){
            if(bin.substring(i-1,i).equals("1")){
                decimal += Math.pow(2,counter);
            }
            counter++;
        }
        return decimal;
    }
}
