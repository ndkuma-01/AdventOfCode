package Utilities.General;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static List<List<Character>> convertToGrid(List<String> t ){
        List<List<Character>> temp = new ArrayList<>();
        for(int i = 0 ; i < t.size(); i++){
            temp.add(t.get(i).chars().mapToObj(x -> (char)x).collect(Collectors.toList()));
        }
        return temp;
    }


}
