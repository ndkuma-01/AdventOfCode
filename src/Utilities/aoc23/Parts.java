package Utilities.aoc23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parts{
    public Parts(){}
    public HashMap<Character, Long> categ = new HashMap<>(Map.of('x', 0L, 'm', 0L, 'a', 0L, 's', 0L));
    public Parts parse(String s){
        List<String> temp = List.of(s.split("\\{")[1].split("}")[0].split(","));
        List<Long> temp1 = new ArrayList<Long>();
        for(int i = 0 ; i < temp.size(); i++) {
            temp1 = temp.stream().mapToLong(x -> Long.parseLong(x.trim().substring(x.indexOf("=") + 1))).boxed().toList();

        }
        String t2 = "xmas";
        int i = 0;
        for(char c : t2.toCharArray()){
            categ.put(c, temp1.get(i));
            i++;
        }
        return this;
    }
    public Long sum(){
        return categ.values().stream().mapToLong(Long::valueOf).sum();
    }
    @Override
    public String toString() {
        return categ.toString();
    }
}
