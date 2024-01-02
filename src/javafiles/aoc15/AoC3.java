package javafiles.aoc15;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc15.HyperDuplex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class AoC3 extends RUNDAY implements DAYID {
    @Override
    public String p1() throws IOException {
        GetInputs input = new GetInputs(15,3);
        int cx = 0;
        int cy = 0;

        HashSet<HyperDuplex> houses = new HashSet<HyperDuplex>();

        String inp = input.wholeFile();
        houses.add(new HyperDuplex(cx,cy));
        for(int i = 0 ; i < inp.length(); i++){
            if(inp.substring(i,i+1).equals(">")){
                cx++;
            }else if(inp.substring(i,i+1).equals("<")){
                cx--;
            }else if(inp.substring(i,i+1).equals("^")){
                cy++;
            }else if(inp.substring(i,i+1).equals("v")){
                cy--;
            }

            houses.add(new HyperDuplex(cx,cy));
        }
        return String.valueOf(houses.size());
    }

    @Override

    public String p2() throws IOException {
        GetInputs input = new GetInputs(15,3);
        int cx = 0;
        int cy = 0;
        int rx = 0;
        int ry = 0;

        HashSet<HyperDuplex> houses = new HashSet<HyperDuplex>();

        String inp = input.wholeFile();
        houses.add(new HyperDuplex(cx,cy));
        for(int i = 0 ; i < inp.length(); i++){
            if(i%2 !=0) {
                if (inp.substring(i, i + 1).equals(">")) {
                    cx++;
                } else if (inp.substring(i, i + 1).equals("<")) {
                    cx--;
                } else if (inp.substring(i, i + 1).equals("^")) {
                    cy++;
                } else if (inp.substring(i, i + 1).equals("v")) {
                    cy--;
                }
                houses.add(new HyperDuplex(cx,cy));

            }else{
                if (inp.substring(i, i + 1).equals(">")) {
                    rx++;
                } else if (inp.substring(i, i + 1).equals("<")) {
                    rx--;
                } else if (inp.substring(i, i + 1).equals("^")) {
                    ry++;
                } else if (inp.substring(i, i + 1).equals("v")) {
                    ry--;
                }
                houses.add(new HyperDuplex(rx, ry));
            }
        }

        return String.valueOf(houses.size());
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC3());
    }
}
