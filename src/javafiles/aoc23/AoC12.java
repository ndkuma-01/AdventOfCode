package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc23.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AoC12 implements DAYID {
    @Override
    public String p1() throws IOException {
        List<Pair<String, List<Integer>>> temp = new GetInputs(23,12).filetoArrayList().stream().map(x-> new Pair<>(x.split(" ")[0], Arrays.asList(x.split(" ")[1].split(",")).stream().mapToInt(y->Integer.parseInt(y.trim())).boxed().collect(Collectors.toList()))).collect(Collectors.toList());
        return String.valueOf(temp.stream().mapToLong(x->  recurse(new HashMap<>(), x.a(), x.b(), new ArrayList<>(List.of(0,0,0)))).sum());

    }


    public long recurse(HashMap<ArrayList<Integer>, Long> map, String locations, List<Integer> nums, ArrayList<Integer> cnts) {
        if(map.containsKey(cnts)){return map.get(cnts);}
        if(cnts.get(0)==locations.length()){return ((cnts.get(1) == nums.size() && cnts.get(2) == 0) || (cnts.get(1) == (nums.size() - 1) && nums.get(cnts.get(1)) == cnts.get(2)))? 1:0;}
        long total = 0 ;
        if((".?".indexOf(locations.charAt(cnts.get(0)))) >-1 && cnts.get(2) == 0){total+= recurse(map, locations, nums, new ArrayList<>(List.of(cnts.get(0) + 1, cnts.get(1), 0)));
        }else if(".?".indexOf(locations.charAt(cnts.get(0))) >-1 &&  cnts.get(2) > 0 && cnts.get(1) < nums.size() && nums.get(cnts.get(1)) == cnts.get(2) ){total +=recurse(map, locations, nums, new ArrayList<>(List.of(cnts.get(0) + 1, cnts.get(1) + 1, 0))); }
        if("#?".indexOf(locations.charAt(cnts.get(0))) > -1){total += recurse(map, locations, nums, new ArrayList<>(List.of(cnts.get(0) + 1, cnts.get(1) , cnts.get(2) + 1)));}
        map.put(cnts, total);
        return total;
    }

    @Override
    public String p2() throws IOException {
        List<Pair<String, List<Integer>>> temp = new GetInputs(23,12).filetoArrayList().stream().map(x-> new Pair<>(String.join("?",x.split(" ")[0],x.split(" ")[0], x.split(" ")[0], x.split(" ")[0], x.split(" ")[0]), nCopyStream(Arrays.asList(x.split(" ")[1].split(",")).stream().mapToInt(y->Integer.parseInt(y.trim())).boxed().collect(Collectors.toList()),5).collect(Collectors.toList()))).collect(Collectors.toList());
        return String.valueOf(temp.stream().mapToLong(x->  recurse(new HashMap<>(), x.a(), x.b(), new ArrayList<>(List.of(0,0,0)))).sum());
    }

    public Stream<Integer> nCopyStream(List<Integer> curr, int n ){
        Stream.Builder<Integer> builder =  Stream.builder();
        for(int i = 0; i < n; i++){
            for(int j = 0 ; j < curr.size(); j++) {
                builder.add(curr.get(j));
            }
        }
        return builder.build();
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC12());
    }
}
