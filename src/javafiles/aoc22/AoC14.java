package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.HyperDuplex;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class AoC14 implements DAYID {
    //this solution uses a custom data type called HyperDuplexes
    //this is a very advanced, versatile, and feature rich coordinate point class made by Nikhil K.
    //feel free to look here: Utilities.aoc22.HyperDuplex, to see more of the features of hyperduplexes

    @Override
    public String p1() throws FileNotFoundException {
        //takes in the input for day 14
        GetInputs input = new GetInputs(22,14);
        //creates a hashset that hold unique coordinate points of where the solid objects are in the simulation
        //although it is called walls it will keep track of all solid objects
        HashSet<HyperDuplex> walls = new HashSet<>();
        //takes in all the lines in the input and puts them to a array seperating by \n
        String[] lines = input.filetoArray();
        //embodies each line
        for(String s : lines){
            //splits the string into an array of parts, seperated by the arrows
            String[] parts = s.split(" -> ");
            //for the entirety of the parts it finds the first coord and the second coord
            for(int i = 1 ; i < parts.length; i++){
                String[] FIRSTCOORD = parts[i-1].split(",");
                String[] SECONDCOORD = parts[i].split(",");
                //parses in the x and y coordinates for the first and second coordinate points
                int x1 = Integer.parseInt(FIRSTCOORD[0]), x2 = Integer.parseInt(SECONDCOORD[0]), y1 = Integer.parseInt(FIRSTCOORD[1]), y2 = Integer.parseInt(SECONDCOORD[1]);
                //this is responsible for ordering the coordinate points by least to greatest in order to avoid some
                // weird errors that could occur later on
                if(x1>x2){
                    int crap = x1;
                    x1=x2;
                    x2=crap;
                }
                if(y1>y2){
                    int crap = y1;
                    y1=y2;
                    y2=crap;
                }
                //if either of them are equal to each other then they are added to the walls for the entirety of their
                //dynamic variables length
                if(x1 == x2){
                    for(int j = y1; j<=y2; j++){
                        walls.add(new HyperDuplex(x1,j));
                    }
                }else if(y1 == y2){
                    for(int j = x1; j <= x2; j++){
                        walls.add(new HyperDuplex(j, y1));
                    }
                }

            }
        }

        int lowest = walls.stream().map(x -> x.y).max(Integer::compare).get();
        //sets a translation coordinate scheme for translating diagonal left and diagonal right
        //this can later be used with the hyperduplex's ability to translate based on adding together coordinate points
        //as you would do with simply matrices
        HyperDuplex diagonalLeft = new HyperDuplex(-1,1);
        HyperDuplex diagonalRight = new HyperDuplex(1,1);
        //the coordinate point of where the sand starts
        HyperDuplex startOfSand = new HyperDuplex(500,0);

        HyperDuplex copy = startOfSand.copy();

        int ans =0 ;
        //this will keep executing until the y level is lower than the lowest y value
        do{
            //essentially this if statement just checks to see if the translated copy of the hyperduplex when translated in
            // multiple directions is contained in the walls directory
            //if not then it will add itself into that direction
            //however if it is for all the directions than it will reach the else branch
            if(!walls.contains(copy.sum(HyperDuplex.DOWN))){
                copy.sumSelf(HyperDuplex.DOWN);
            }else if(!walls.contains(copy.sum(diagonalLeft))){
                copy.sumSelf(diagonalLeft);
            } else if (!walls.contains(copy.sum(diagonalRight))) {
                copy.sumSelf(diagonalRight);
            }else{
                //here the answer is incremented and the new solid object is taken into account
                walls.add(copy.copy());
                copy = startOfSand.copy();
                ans++;
            }
        }while(copy.y < lowest);
        //returns answer
        return String.valueOf(ans);
    }

    @Override
    public String p2() throws FileNotFoundException {
        //takes in the input for day 14
        GetInputs input = new GetInputs(22,14);
        //creates a hashset that hold unique coordinate points of where the solid objects are in the simulation
        //although it is called walls it will keep track of all solid objects
        HashSet<HyperDuplex> walls = new HashSet<>();
        //takes in all the lines in the input and puts them to a array seperating by \n
        String[] lines = input.filetoArray();
        //embodies each line
        for(String s : lines){
            //splits the string into an array of parts, seperated by the arrows
            String[] parts = s.split(" -> ");
            //for the entirety of the parts it finds the first coord and the second coord
            for(int i = 1 ; i < parts.length; i++){
                String[] FIRSTCOORD = parts[i-1].split(",");
                String[] SECONDCOORD = parts[i].split(",");
                //parses in the x and y coordinates for the first and second coordinate points
                int x1 = Integer.parseInt(FIRSTCOORD[0]), x2 = Integer.parseInt(SECONDCOORD[0]), y1 = Integer.parseInt(FIRSTCOORD[1]), y2 = Integer.parseInt(SECONDCOORD[1]);
                //this is responsible for ordering the coordinate points by least to greatest in order to avoid some
                // weird errors that could occur later on
                if(x1>x2){
                    int crap = x1;
                    x1=x2;
                    x2=crap;
                }
                if(y1>y2){
                    int crap = y1;
                    y1=y2;
                    y2=crap;
                }
                //if either of them are equal to each other then they are added to the walls for the entirety of their
                //dynamic variables length
                if(x1 == x2){
                    for(int j = y1; j<=y2; j++){
                        walls.add(new HyperDuplex(x1,j));
                    }
                }else if(y1 == y2){
                    for(int j = x1; j <= x2; j++){
                        walls.add(new HyperDuplex(j, y1));
                    }
                }

            }
        }
        int lowest = walls.stream().map(x -> x.y).max(Integer::compare).get();
        int num = 500 - (2*lowest);
        int threshol = 500 + (2*lowest);
        for(int j =num; j < threshol; j++){
            walls.add(new HyperDuplex(j, lowest+2));
        }
        //sets a translation coordinate scheme for translating diagonal left and diagonal right
        //this can later be used with the hyperduplex's ability to translate based on adding together coordinate points
        //as you would do with simply matrices
        HyperDuplex diagonalLeft = new HyperDuplex(-1,1);
        HyperDuplex diagonalRight = new HyperDuplex(1,1);
        HyperDuplex startOfSand = new HyperDuplex(500,0);

        HyperDuplex copy = startOfSand.copy();

        int ans =0 ;
        //this will keep executing for as long as the source block isn't a wall (aka solid object)
        do{
            //essentially this if statement just checks to see if the translated copy of the hyperduplex when translated in
            // multiple directions is contained in the walls directory
            //if not then it will add itself into that direction
            //however if it is for all the directions than it will reach the else branch
            if(!walls.contains(copy.sum(HyperDuplex.DOWN))){
                copy.sumSelf(HyperDuplex.DOWN);
            }else if(!walls.contains(copy.sum(diagonalLeft))){
                copy.sumSelf(diagonalLeft);
            } else if (!walls.contains(copy.sum(diagonalRight))) {
                copy.sumSelf(diagonalRight);
            }else{
                //here the answer is incremented and the new solid object is taken into account
                walls.add(copy.copy());
                copy = startOfSand.copy();
                ans++;
            }
        }while(!walls.contains(startOfSand));
        return String.valueOf(ans);

    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC14());
    }
}
