package Utilities.General;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GetInputs {
    int day = 0;
    String file = "";
    Scanner stdin;

    public GetInputs(int year,int day) throws FileNotFoundException {
        String y = convertYearToPath(year);
        this.day = day;
        file =  "C:\\Users\\nkh15448\\IdeaProjects\\AdventOfCode\\src\\inputs\\"+ y + "\\input" +  + day + ".txt";
        stdin = new Scanner(new File(file));
    }

    public boolean hasLines() throws FileNotFoundException {
        return stdin.hasNextLine();
    }
    public String nextLine(){
        return stdin.nextLine();
    }
    public int nextInt(){
        return stdin.nextInt();
    }
    public String next(){
        return stdin.next();
    }
    public long nextLong(){
        return stdin.nextLong();
    }
    public double nextDouble(){
        return stdin.nextDouble();
    }

    public String wholeFile(){
        String result = "";
        while(stdin.hasNextLine()){

            result += stdin.nextLine();
            if(!(stdin.hasNextLine())){
                result += "\n";
            }
        }
        return result;
    }
    public ArrayList<String> filetoArrayList(){
        ArrayList<String> result = new ArrayList<String>();
        while(stdin.hasNextLine()){
            result.add(stdin.nextLine());
        }
        return result;
    }
    public String[] filetoArray(){
        ArrayList<String> result = new ArrayList<String>();
        while(stdin.hasNextLine()){
            result.add(stdin.nextLine());
        }
        String[] retrn = new String[result.size()];
        for(int i = 0; i < result.size(); i++){
            retrn[i] = result.get(i);
        }
        return retrn;
    }

    public String convertYearToPath(int y){
        HashMap<Integer,String> map = new HashMap<Integer,String>();
        map.put(1,"one");
        map.put(2,"two");
        map.put(3,"three");
        map.put(4,"four");
        map.put(5,"five");
        map.put(6,"six");
        map.put(7,"seven");
        map.put(8,"eight");
        map.put(9,"nine");
       String tens = map.get(Integer.parseInt(String.valueOf(y).substring(0,1)));
       String ones = map.get(Integer.parseInt(String.valueOf(y).substring(1,2)));

       return tens + ones;

    }

}
