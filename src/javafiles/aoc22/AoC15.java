package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AoC15 implements DAYID{
    //this solution could have been made much simpler if hyper duplexes were implemented however this was made before the creation
    //of hyper duplexes
    public static int min(ArrayList<int[]> intervals){
        int smallMin = Integer.MAX_VALUE;
        for(int i = 0 ; i < intervals.size();i++){
            if(smallMin >intervals.get(i)[0]){
                smallMin = intervals.get(i)[0];
            }
        }
        return smallMin;
    }
    public static int max(ArrayList<int[]> intervals){
        int smallMax = Integer.MIN_VALUE;
        for(int i = 0 ; i < intervals.size();i++){
            if(smallMax < intervals.get(i)[1]){
                smallMax = intervals.get(i)[1];
            }
        }
        return smallMax;
    }
    public static int dist(int[] a, int[] b){
        //finds the manhattan distance of two coordinate points
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }
    public static int getNum(String a){
        int index = 0;
        int index2 = 0;
        for(int i =0 ; i < a.length(); i++){
            if(a.substring(i,i+1).equals("=")){
                index = i;
                break;
            }
        }
        return Integer.parseInt(a.substring(index+1, a.length()-1));
    }
    public static int getBY(String a){
        int index = 0;
        for(int i =0 ; i < a.length(); i++){
            if(a.substring(i,i+1).equals("=")){
                index = i;
                break;
            }
        }
        return Integer.parseInt(a.substring(index+1));
    }

    @Override
    public String p1() throws IOException {
        //takes in the input for day 15
        GetInputs input = new GetInputs(22,15);
        ArrayList<String> stuff = new ArrayList<String>();
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<Integer> sx = new ArrayList<Integer>();
        ArrayList<Integer> sy = new ArrayList<Integer>();
        ArrayList<Integer> bx = new ArrayList<Integer>();
        ArrayList<Integer> by = new ArrayList<Integer>();
        //creates an arraylist of int arrays of senesors and beacons
        //whenever you see int[] these will always just be length 2 arrays that hold the x and the y coord points
        ArrayList<int[]> sensors = new ArrayList<int[]>();
        ArrayList<int[]> beacons = new ArrayList<int[]>();
        //goes through each of the lines
        while(input.hasLines()){
            //takes the parts of each line
            String[] temp = input.nextLine().split(" ");
            for(int i = 0 ; i < temp.length; i++){
                inputs.add(temp[i]);
            }
        }
        //adds all sensor and beacon coordinate points
        //the act of parsing is very inefficient and could have been made much quicker if I had used the .split() command

        for(int i = 0 ; i < inputs.size() ; i+=10 ){
            //these methods are just responsible for isolating the coordinate points away from the other crap in the input
            //again this is an extremely inefficient method, but it still operates relatively quick
            sx.add(getNum(inputs.get(i+2)));
            sy.add(getNum(inputs.get(i+3)));
            bx.add(getNum(inputs.get(i+8)));
            by.add(getBY(inputs.get(i+9)));
        }

        //goes through however many coordinate points there and adds them to the sensors and the becaons
        for(int i = 0 ; i < sx.size();i++){
            int[] sens = {sx.get(i), sy.get(i)};
            int[] beac = {bx.get(i),by.get(i)};
            sensors.add(sens);
            beacons.add(beac);

        }
        int N = sensors.size();
        ArrayList<Integer> dists = new ArrayList<Integer>();
        for(int i = 0 ; i< N; i++){
            dists.add(dist(sensors.get(i),beacons.get(i)));
        }
        int Y = 2000000;

        ArrayList<int[]> intervals = new ArrayList<int[]>();
        // in essence this method will find the union of intervals that are contained in the radius of a sensor and a beacon
        //since we will know the intervals that are on y = 2000000, we can then count however many positions are
        for(int i = 0 ; i< sensors.size(); i++){
            //this dx stands for delta x and it finds the change in x from the center point to the edges of the sensor radius
            //also keep in mind that each of the areas encompassed by the sensors radius is a square, so when the term radius is used
            //it is in reference to the distance from the center point to one of the corners of the square
            int dx = dists.get(i) - Math.abs(sensors.get(i)[1] - Y);

            if(dx <= 0){
                continue;
            }
            int[] temp = {sensors.get(i)[0] - dx, sensors.get(i)[0] + dx};
            //the interval is added to be analyzed later
            intervals.add(temp);
        }
        ArrayList<Integer> goodX = new ArrayList<Integer>();
        for(int i = 0 ; i < beacons.size(); i++){
            if(beacons.get(i)[1] == Y){
                goodX.add(beacons.get(i)[0]);
            }
        }
        //finds the smallest and biggest x
        int min_x = min(intervals);
        int max_x = max(intervals);
        int answer = 0;
        //and sees if it is contained by goodX
        for(int i = min_x; i <= max_x; i++){
            if (goodX.contains(i)){
                continue;
            }
            for(int j = 0 ; j < intervals.size(); j++){
                int left = intervals.get(j)[0];
                int right = intervals.get(j)[1];
                if( left <= i  && i <= right){
                    answer+=1;
                    break;
                }
            }
        }
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws IOException {
        //gets the input for day 15
    GetInputs input = new GetInputs(22,15);
        ArrayList<String> stuff = new ArrayList<String>();
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<Integer> sx = new ArrayList<Integer>();
        ArrayList<Integer> sy = new ArrayList<Integer>();
        ArrayList<Integer> bx = new ArrayList<Integer>();
        ArrayList<Integer> by = new ArrayList<Integer>();
        ArrayList<int[]> sensors = new ArrayList<int[]>();
        ArrayList<int[]> beacons = new ArrayList<int[]>();
        //parses through each of the lines
        while(input.hasLines()){
            String[] temp = input.nextLine().split(" ");
            for(int i = 0 ; i < temp.length; i++){
                inputs.add(temp[i]);
            }
        }
        //dissects each line for the necessary info,
        //this process could be made much more efficient with better use of the .split() method
        for(int i = 0 ; i < inputs.size() ; i+=10 ){
            sx.add(getNum(inputs.get(i+2)));
            sy.add(getNum(inputs.get(i+3)));
            bx.add(getNum(inputs.get(i+8)));
            by.add(getBY(inputs.get(i+9)));
        }

        for(int i = 0 ; i < sx.size();i++){
            //finds the coord points of the sensors and the beacons
            int[] sens = {sx.get(i), sy.get(i)};
            int[] beac = {bx.get(i),by.get(i)};

            sensors.add(sens);
            beacons.add(beac);

        }

        int N = sensors.size();
        ArrayList<Integer> dists = new ArrayList<Integer>();
        for(int i = 0 ; i< N; i++){
            dists.add(dist(sensors.get(i),beacons.get(i)));
        }
        //this method takes the equations for the positive and negative lines that border to make each one of the square areaas
        //that surround the area encompassed by each sensor to the nearest beacon
        //this can be later used with some clever math to figure out intersection points and find where there is no intersection
        //at all
        //this would be the location of the distress signal
        ArrayList<Integer> neg_lines = new ArrayList<Integer>();
        ArrayList<Integer> pos_lines = new ArrayList<Integer>();

        for(int i = 0; i < sensors.size(); i++){
            int d = dists.get(i);

            neg_lines.add(sensors.get(i)[0] + sensors.get(i)[1] - d);
            neg_lines.add(sensors.get(i)[0] + sensors.get(i)[1] + d);
            pos_lines.add(sensors.get(i)[0] - sensors.get(i)[1] - d);
            pos_lines.add(sensors.get(i)[0] - sensors.get(i)[1] + d);
        }

        int neg =0;
        int pos = 0;

        for(int i = 0 ; i < 2*N; i++){
            for(int j =  i+1; j < 2*N; j++){
                int a = pos_lines.get(i);
                int b = pos_lines.get(j);

                if(Math.abs(a-b)==2){
                    pos = Math.min(a,b) +1;
                }
                a = neg_lines.get(i);
                b = neg_lines.get(j);
                if(Math.abs(a-b) ==2){
                    neg = Math.min(a, b) + 1;
                }
            }
        }
        long x = (pos + neg)/2;
        long y = (neg - pos)/2;
        long answer = x * 4000000 + y;
        return Long.toString(answer);
    }
    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC15());
    }
}
