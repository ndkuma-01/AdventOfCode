package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class AoC8 implements DAYID {



    @Override
    public String p1() throws IOException {
        GetInputs in = new GetInputs(23,8);
        HashMap<String, HashMap<String,String>> LRS = new HashMap<>();
        HashMap<String, String> left = new HashMap<>();
        HashMap<String , String> right = new HashMap<>();
        LRS.put("L", left);
        LRS.put("R", right);

        String directions = in.nextLine();
        in.nextLine();
        while(in.hasLines()){
            String line = in.nextLine();
            String[] temp = line.split("=");
            String curr = temp[0].trim();
            String[] LR = temp[1].split(",");
            String l = LR[0].split("\\(")[1].trim();
            String r = LR[1].split("\\(")[0].trim().split("\\)")[0].trim();
            LRS.get("L").put(curr, l);
            LRS.get("R").put(curr, r);
        }

        ArrayList<String> POS = new ArrayList<>();
        for(String s : LRS.get("L").keySet()){
            if(s.endsWith("AAA")){
                POS.add(s);
            }
        }
        HashMap<Integer, Integer> T = new HashMap<>();
        int t = 0;
        while(true){
            int i = 0 ;
            ArrayList<String> NP = new ArrayList<>();

            for(String p : POS) {
                p = LRS.get(String.valueOf(directions.charAt(t%directions.length()))).get(p);
                if(p.endsWith("Z")){
                    T.put(i, t+1);
                    if(T.size() == POS.size()){
//                        System.out.println(T.values());
                        return String.valueOf(lcm(T.values()));
                    }
                }
                NP.add(p);

                i++;
            }
            POS = NP;
            t++;

        }

    }

    @Override
    public String p2() throws IOException {
        GetInputs in = new GetInputs(23,8);
        HashMap<String, HashMap<String,String>> LRS = new HashMap<>();
        HashMap<String, String> left = new HashMap<>();
        HashMap<String , String> right = new HashMap<>();
        LRS.put("L", left);
        LRS.put("R", right);

        String directions = in.nextLine();
        in.nextLine();
        while(in.hasLines()){
            String line = in.nextLine();
            String[] temp = line.split("=");
            String curr = temp[0].trim();
            String[] LR = temp[1].split(",");
            String l = LR[0].split("\\(")[1].trim();
            String r = LR[1].split("\\(")[0].trim().split("\\)")[0].trim();
            LRS.get("L").put(curr, l);
            LRS.get("R").put(curr, r);
        }

        ArrayList<String> POS = new ArrayList<>();
        for(String s : LRS.get("L").keySet()){
            if(s.endsWith("A")){
                POS.add(s);
            }
        }
        HashMap<Integer, Integer> T = new HashMap<>();
        int t = 0;
        while(true){
            int i = 0 ;
            ArrayList<String> NP = new ArrayList<>();

            for(String p : POS) {
                p = LRS.get(String.valueOf(directions.charAt(t%directions.length()))).get(p);
                if(p.endsWith("Z")){
                    T.put(i, t+1);
                    if(T.size() == POS.size()){
                        System.out.println(T.values());
                        return String.valueOf(lcm(T.values()));
                    }
                }
                NP.add(p);

                i++;
            }
            POS = NP;
            t++;

        }
    }




    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC8());
    }


public Long lcm(Collection<Integer> nums){
    Long res = 1L;
    for(Long num :  nums.stream().mapToLong(Long::valueOf).boxed().collect(Collectors.toList())){
        res = (num * res)/ gcd(num, res);
    }
    return res;
}

    public Long gcd(Long x, Long y) {
        if(y <= 0){
            return x;
        }else{
            return gcd(y, x%y);
        }
    }


}



