package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;
import Utilities.aoc23.Parts;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AoC19 implements DAYID {
    public HashMap<String, List<String>> map = new HashMap<>();
    HashMap<String, Pair<ArrayList<ArrayList<String>>, String>> wF = new HashMap<>();
    @Override
    public String p1() throws IOException {
        List<String> file =  new GetInputs(23,19).filetoArrayList();
        List<List<String>> worksParts = List.of(file.stream().takeWhile(s -> !s.equals("")).collect(Collectors.toList()), IntStream.range(file.indexOf("")+1, file.size()).mapToObj(k -> file.get(k)).collect(Collectors.toList()));
        List<String> names = worksParts.get(0).stream().map(x -> x.substring(0, x.indexOf('{'))).toList();
        IntStream.range(0, worksParts.get(0).size()).forEach(i-> map.put(names.get(i), Arrays.stream(worksParts.get(0).get(i).trim().split("\\{")[1].split("}")[0].split(",")).map(String::trim).toList()));
       return String.valueOf(worksParts.get(1).stream().map(x -> new Parts().parse(x)).toList().stream().filter(x-> getGoodParts(x)).mapToLong(y-> y.sum()).sum());
    }

    public boolean getGoodParts(Parts p){
        String curr = "in";
        checkpoint1:
        {
            while (true) {
                checkpoint2:
                {
                    List<String> rules = map.get(curr);
                    for (int j = 0; j < rules.size(); j++) {
                        String currRule = rules.get(j);
                        if (currRule.contains("<")) {
                            String[] parts = currRule.split(":");
                            if (p.categ.get(parts[0].split("<")[0].trim().charAt(0)) < Long.parseLong(parts[0].split("<")[1].trim())) {
                                if (parts[1].equals("A")) {
                                    return true;
                                } else if (parts[1].equals("R")) {
                                    return false;
                                } else {
                                    curr = parts[1];
                                    break checkpoint2;
                                }
                            }
                        }else if(currRule.contains(">")){
                            String[] parts = currRule.split(":");
                            if (p.categ.get(parts[0].split(">")[0].trim().charAt(0)) > Long.parseLong(parts[0].split(">")[1].trim())) {
                                if (parts[1].equals("A")) {
                                    return true;
                                } else if (parts[1].equals("R")) {
                                    return false;
                                } else {
                                    curr = parts[1];
                                    break checkpoint2;
                                }
                            }
                        }else{
                            if (currRule.equals("A")) {
                                return true;
                            } else if (currRule.equals("R")) {
                                return false;
                            } else {
                                curr = currRule;
                                break checkpoint2;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public String p2() throws IOException {
        List<String> file = new GetInputs(23,19).filetoArrayList();
        List<List<String>> worksParts = List.of(file.stream().takeWhile(s -> !s.equals("")).collect(Collectors.toList()), IntStream.range(file.indexOf("")+1, file.size()).mapToObj(k -> file.get(k)).collect(Collectors.toList()));
        for(int i = 0 ; i < worksParts.get(0).size(); i++){
            String currLine = worksParts.get(0).get(i), name = currLine.split("\\{")[0].trim(),temp = currLine.substring(currLine.lastIndexOf(',') + 1, currLine.length() - 1);
            List<String> rules = List.of(currLine.split("\\{")[1].split("}")[0].split(",")).subList(0, List.of(currLine.split("\\{")[1].split("}")[0].split(",")).size() - 1);
            wF.put(name, new Pair<>(new ArrayList<ArrayList<String>>(), temp));
            for(String rule : rules){
                wF.get(name).a().add(new ArrayList<>(List.of(rule.split(":")[0].substring(0,1).trim(), rule.split(":")[0].substring(1,2).trim(), rule.split(":")[0].substring(2).trim(), rule.split(":")[1].trim())));
            }
        }
        HashMap<Character, Pair<Long, Long>> plsWork = new HashMap<>(Map.of('x', new Pair<>(1L,4000L), 'm',new Pair<>(1L,4000L), 'a', new Pair<>(1L,4000L), 's',new Pair<>(1L,4000L)));
        return String.valueOf(recurse(plsWork, "in"));
    }
    public Long recurse(HashMap<Character, Pair<Long, Long>> r, String curr){
        if(curr.equals("A")){
            return r.values().stream().mapToLong(pair -> pair.b() - pair.a() + 1).reduce(1L, (x,y) -> x * y);
        }
        if(curr.equals("R")){
            return 0L;
        }
        Long total = 0L;
        Pair<ArrayList<ArrayList<String>>, String> temp = wF.get(curr);
        for(int i = 0 ; i < temp.a().size(); i++){
            Pair<Long, Long> minMax = r.get(temp.a().get(i).get(0).charAt(0)), corr = (temp.a().get(i).get(1).equals("<"))? new Pair<>( minMax.a(), Math.min(Long.parseLong(temp.a().get(i).get(2).trim()) - 1, minMax.b())) :new Pair<>(Math.max(Long.parseLong(temp.a().get(i).get(2).trim()) + 1, minMax.a()), minMax.b()) , fals = (temp.a().get(i).get(1).equals("<"))? new Pair<>(Math.max(Long.parseLong(temp.a().get(i).get(2).trim()), minMax.a()), minMax.b()) : new Pair<>( minMax.a(), Math.min(Long.parseLong(temp.a().get(i).get(2).trim()), minMax.b()));
            if(corr.a() <= corr.b()){
                HashMap<Character, Pair<Long, Long>> r2 = (HashMap<Character, Pair<Long, Long>>) r.clone();
                r2.put(temp.a().get(i).get(0).charAt(0), corr);
                total += recurse(r2, temp.a().get(i).get(3));
            }
            if(fals.a() <= fals.b()){
                r.put(temp.a().get(i).get(0).charAt(0), fals);
            }else{
                break;
            }
        }
        total += recurse(r, temp.b());
        return total;
    }
    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC19());
    }
}