package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javafiles.aoc23.AoC8;
public class AoC20 implements DAYID {


    @Override
    public String p1() throws IOException {
        List<List<String>> in = new GetInputs(23,20).filetoArrayList().stream().map(x -> Arrays.asList(x.split(" -> "))).collect(Collectors.toList());
        List<String> bcInit = new ArrayList<>();
        HashMap<String, Module> mods = new HashMap<>();
        in.forEach(x->{
            String left = x.get(0);
            List<String> right = Arrays.asList(x.get(1).split(", ")).stream().map(y->y.trim()).toList();
            if(left.trim().equals("broadcaster")){
                bcInit.addAll(right);
            }else{
                mods.put(left.trim().substring(1).trim() , new Module(left.trim().substring(1).trim(),right, left.trim().charAt(0)) );
            }
        });
        for(Map.Entry<String, Module> entry : mods.entrySet()){
            for(String s : entry.getValue().connects){
                if(mods.containsKey(s) && mods.get(s).t=='&'){
                    mods.get(s).previous.put(entry.getKey(), "lo");
                }
            }
        }
        AtomicReference<Long> lo = new AtomicReference<>(0L), hi = new AtomicReference<>(0L);

        LongStream.range(0,1000).forEach(x->{
            lo.getAndSet(lo.get() + 1);

            Deque<ArrayList<String>> d = new ArrayDeque<>();
            bcInit.stream().forEach(y->d.add(new ArrayList<String>( List.of("broadcaster", y, "lo"))));
            int i = 0;
            while(i < d.size()){

                List<String> temp =d.pop();
                if(temp.get(2).equals("lo")){
                    lo.getAndSet(lo.get() + 1);
                }else{
                    hi.getAndSet(hi.get() + 1);
                }

                if(!mods.containsKey(temp.get(1).trim())){
                    continue;
                }
                Module curr = mods.get(temp.get(1));
                if(curr.t == '%'){
                    if (temp.get(2).equals("lo")) {
                        curr.toggle = (curr.toggle.equals("off"))? "on" : "off";
                        String out = (curr.toggle.equals("on"))? "hi" : "lo";
                        curr.connects.forEach(y -> {
                            d.add(new ArrayList<>(List.of(curr.modName, y, out)));
                        });

                    }
                }else{
                    curr.previous.put(temp.get(0),temp.get(2));
                    String out = (curr.previous.values().stream().filter(z->z.equals("hi")).collect(Collectors.toList()).size() == curr.previous.values().size())? "lo" : "hi";
//                    System.out.println("out: " + out);
                    curr.connects.forEach(z -> {
                        d.add(new ArrayList<>(List.of(curr.modName, z, out)));
                    });
                }


            }


        });

        return String.valueOf(lo.get() * hi.get());
    }


    // I had no clue how to solve this one tbh
    //I took the time to understand how other people went about solving a problem
    // and found inspiration from various users on reddit
    // a common method utilized by people was to map backwards and
    // find the lcm of the module's cycles that feed into rx
    // here is my java implementation of it:
    @Override
    public String p2() throws IOException {
        List<List<String>> in = new GetInputs(23,20).filetoArrayList().stream().map(x -> Arrays.asList(x.split(" -> "))).collect(Collectors.toList());
        List<String> bcInit = new ArrayList<>();
        HashMap<String, Module> mods = new HashMap<>();
        in.forEach(x->{
            String left = x.get(0);
            List<String> right = Arrays.asList(x.get(1).split(", ")).stream().map(y->y.trim()).toList();
            if(left.trim().equals("broadcaster")){
                bcInit.addAll(right);
            }else{
                mods.put(left.trim().substring(1).trim() , new Module(left.trim().substring(1).trim(),right, left.trim().charAt(0)) );
            }
        });
        for(Map.Entry<String, Module> entry : mods.entrySet()){
            for(String s : entry.getValue().connects){
                if(mods.containsKey(s) && mods.get(s).t=='&'){
                    mods.get(s).previous.put(entry.getKey(), "lo");
                }
            }
        }
        String rx = "";
        HashMap<String, Long> visisted = new HashMap<>();
        HashMap<String, Long> cL = new HashMap<>();
        for(Map.Entry<String, Module> entry : mods.entrySet()){
            if(entry.getValue().connects.contains("rx")){
                rx = entry.getKey();
                break;
            }
        }

        for(Map.Entry<String, Module> entry : mods.entrySet()){
            if(entry.getValue().connects.contains(rx)){
                visisted.put(entry.getKey(), 0L);
            }
        }
        System.out.println(visisted);

        AtomicReference<Long> lo = new AtomicReference<>(0L), hi = new AtomicReference<>(0L);
        Long x = 0L;
       while(true) {
           x++;
           lo.getAndSet(lo.get() + 1);

           Deque<ArrayList<String>> d = new ArrayDeque<>();
           bcInit.stream().forEach(y -> d.add(new ArrayList<String>(List.of("broadcaster", y, "lo"))));
           int i = 0;
           while (i < d.size()) {

               List<String> temp = d.pop();
               if (temp.get(2).equals("lo")) {
                   lo.getAndSet(lo.get() + 1);
               } else {
                   hi.getAndSet(hi.get() + 1);
               }

               if (!mods.containsKey(temp.get(1).trim())) {
                   continue;
               }
               Module curr = mods.get(temp.get(1));
               if(curr.modName.equals(rx) && temp.get(2).equals("hi")){
                   visisted.put(temp.get(0), visisted.getOrDefault(temp.get(0),0L) + 1L);
                   if(!cL.containsKey(temp.get(0))){
                       cL.put(temp.get(0), x);
                   }
                   System.out.println(visisted.values().stream().allMatch(z -> z==visisted.values().iterator().next()));
                   if(visisted.values().stream().allMatch(z -> z==visisted.values().iterator().next())){
                       Long temp3 = 1L;
                       for (Long l : cL.values()){
                           temp3 = (temp3 *l)/ gcd(temp3, l);
                       }
                       return String.valueOf(temp3);
                   }

               }
               if (curr.t == '%') {
                   if (temp.get(2).equals("lo")) {
                       curr.toggle = (curr.toggle.equals("off")) ? "on" : "off";
                       String out = (curr.toggle.equals("on")) ? "hi" : "lo";
                       curr.connects.forEach(y -> {
                           d.add(new ArrayList<>(List.of(curr.modName, y, out)));
                       });

                   }
               } else {
                   curr.previous.put(temp.get(0), temp.get(2));
                   String out = (curr.previous.values().stream().filter(z -> z.equals("hi")).collect(Collectors.toList()).size() == curr.previous.values().size()) ? "lo" : "hi";
                   curr.connects.forEach(z -> {
                       d.add(new ArrayList<>(List.of(curr.modName, z, out)));
                   });
               }


           }


       }
    }

     public Long gcd(Long x, Long y) {
        if(y <= 0){
            return x;
        }else{
            return gcd(y, x%y);
        }
    }

    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC20());
    }
}



class Module{
    String modName;
    Character t;
    List<String> connects;
    String toggle = null;
    HashMap<String, String> previous;

    public Module(String n, List<String> k, Character t ){
            this.modName = n;
            this.connects = k;
            this.t = t;
            toggle = (t == '%')? "off": null;
            previous = new HashMap<>();
    }

    @Override
    public String toString() {
        return    "name: " + modName +  "   type: " + t + "    connections: " + connects +  "    toggle: " + toggle +  "   previous: " + previous + '\n';
    }
}