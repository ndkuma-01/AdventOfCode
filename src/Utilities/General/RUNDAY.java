package Utilities.General;

import java.io.FileNotFoundException;

public class RUNDAY {
    public static void run(DAYID d) throws FileNotFoundException {
        String temp = d.toString();
        String result = "DAY ";
        int i = temp.indexOf("@");
        int j = temp.indexOf("C");
        result += temp.substring(j+1,i);
        System.out.println("------"+result+"------");
        long part1Start = System.currentTimeMillis();
        String part1 = d.p1();
        if(part1 == null) part1 = "Part 1 hasn't been made yet";
        System.out.println("PART 1: " + part1);
        long part1End = System.currentTimeMillis();
        System.out.println("ELAPSED RUNTIME: " + ((part1End - part1Start)/1000.0) + " SECONDS" );
        long part2Start = System.currentTimeMillis();
        String part2 = d.p2();
        if(part2 == null) part2 = "Part 2 hasn't been made yet";
        System.out.println("PART 2: " + part2);
        long part2End = System.currentTimeMillis();
        System.out.println("ELAPSED RUNTIME: " + ((part2End - part2Start)/1000.0) + " SECONDS" );
    }


}
