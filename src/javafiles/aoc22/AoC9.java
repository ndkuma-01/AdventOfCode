package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.Duplex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AoC9 implements DAYID{

    static double hx = 0;
    static double hy = 0;
    static double tx = 0;
    static double ty = 0;
    static ArrayList<Duplex> vertexes = new ArrayList<Duplex>();

    @Override
    public String p1() throws IOException {
        //goes through and takes in the inputs for day nine
        GetInputs input = new GetInputs(22,9);
        //creates an arraylist of all the lines
        ArrayList<String> inp = new ArrayList<String>();
        while (input.hasLines()) {
            inp.add(input.nextLine());
        }
        //creates a hashmap of the directions for easy manipulation of coordinate points later on
        HashMap<String, double[]> directions = new HashMap<String, double[]>();
        double[] right = {1, 0};
        double[] left = {-1, 0};
        double[] up = {0, 1};
        double[] down = {0, -1};
        directions.put("R", right);
        directions.put("U", up);
        directions.put("L", left);
        directions.put("D", down);
        //creates a hashset of visited coordinate points the tail has visited
        //since its a hashset each point will only be counted once
        HashSet<ArrayList<Double>> vt = new HashSet<ArrayList<Double>>();
        ArrayList<Double> temp = new ArrayList<Double>();
        temp.add(tx);
        temp.add(ty);
        vt.add(temp);
        for (int i = 0; i < inp.size(); i++) {
            String direction = inp.get(i).substring(0, 1);
            int amount = Integer.parseInt(inp.get(i).substring(2));
            double dx = directions.get(direction)[0];
            double dy = directions.get(direction)[1];
            //finds the proposed directions for movement moves the parser
            for (int j = 0; j < amount; j++) {
                move(dx, dy);
                ArrayList<Double> temp2 = new ArrayList<Double>();
                temp2.add(tx);
                temp2.add(ty);
                //adds to the hashset
                vt.add(temp2);
            }
        }
        //finds how many unique values there are
            return String.valueOf(vt.size());

    }
    public static boolean touch(double x1, double x2, double y1, double y2) {
        //sees if two points are touching
        boolean hi = ((Math.abs(x1 - x2) <= 1) && (Math.abs(y1 - y2) <= 1));
        return hi;
    }
    public static void move(double dx, double dy) {
        //move method used for problem one
        //does not take into accoutn vertexes

        hx += dx;
        hy += dy;

        if (!touch(hx, tx, hy, ty)) {
            //if they are not touching then it will propose a move
            double sX;
            double sY;
            if (hx == tx) {
                sX = 0;
            } else {
                sX = ((hx - tx) / (Math.abs(hx - tx)));
            }
            if (hy == ty) {
                sY = 0;
            } else {
                sY = ((hy - ty) / (Math.abs(hy - ty)));
            }
            //increments them to their new locale
            tx += sX;
            ty += sY;
        }
    }
    public static void move2(double dx, double dy) {
        //this move2 function is used in the second part and keeps track of the vertexes visited along the moves
        vertexes.get(0).setFirstNum(vertexes.get(0).getFirstNum() + dx);
        vertexes.get(0).setSecNum(vertexes.get(0).getSecNum() + dy);
        for (int i = 1; i < 10; i++) {
            //takes in the positions of the head and the tail
            double hx = vertexes.get(i - 1).getFirstNum();
            double hy = vertexes.get(i - 1).getSecNum();
            double tx = vertexes.get(i).getFirstNum();
            double ty = vertexes.get(i).getSecNum();
            if (!touch(hx, tx, hy, ty)) {
                double sX;
                double sY;
                if (hx == tx) {
                    sX = 0;
                } else {
                    sX = ((hx - tx) / (Math.abs(hx - tx)));
                }
                if (hy == ty) {
                    sY = 0;
                } else {
                    sY = ((hy - ty) / (Math.abs(hy - ty)));
                }

                tx += sX;
                ty += sY;

                vertexes.get(i).setFirstNum(tx);
                vertexes.get(i).setSecNum(ty);

            }
        }

    }
    @Override
    public String p2() throws IOException {
        //takes in the input for day 9
        GetInputs input = new GetInputs(22,9);
        //takes in the lines
        ArrayList<String> inp = new ArrayList<String>();
        while (input.hasLines()) {
            inp.add(input.nextLine());
        }
        //adds empty points to the vertexes
        //these will be manipulated later on
        for (int i = 0; i < 10; i++) {
            Duplex temp = new Duplex(0, 0);
            vertexes.add(temp);
        }
        //creates a hashmap of the directions for easy manipulation later on
        HashMap<String, double[]> directions = new HashMap<String, double[]>();
        double[] right = {1, 0};
        double[] left = {-1, 0};
        double[] up = {0, 1};
        double[] down = {0, -1};
        directions.put("R", right);
        directions.put("U", up);
        directions.put("L", left);
        directions.put("D", down);

//        HashSet<double[]> vt = new HashSet<double[]>();
        //arraylists are used due to their better integration to be compared to one another
        //then compared to normal arrays
        HashSet<ArrayList<Double>> vt = new HashSet<ArrayList<Double>>();
        ArrayList<Double> temp = new ArrayList<Double>();
        temp.add(vertexes.get(vertexes.size() - 1).getFirstNum());
        temp.add(vertexes.get(vertexes.size() - 1).getSecNum());
        vt.add(temp);

        for (int i = 0; i < inp.size(); i++) {
            String direction = inp.get(i).substring(0, 1);
            int amount = Integer.parseInt(inp.get(i).substring(2));
            double dx = directions.get(direction)[0];
            double dy = directions.get(direction)[1];

            for (int j = 0; j < amount; j++) {
                move2(dx, dy);
//               double[] temp2 a= {tx,ty};
                ArrayList<Double> temp2 = new ArrayList<Double>();
                temp2.add(vertexes.get(vertexes.size() - 1).getFirstNum());
                temp2.add(vertexes.get(vertexes.size() - 1).getSecNum());
                vt.add(temp2);

            }
        }
        return String.valueOf(vt.size());
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC9());
    }
}
