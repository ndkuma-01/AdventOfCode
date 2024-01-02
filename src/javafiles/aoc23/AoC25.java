package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AoC25 implements DAYID {


    @Override
    public String p1() throws IOException {
        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        new GetInputs(23,25).filetoArrayList().stream().forEach(l ->{
            List<String> temp = Stream.of(Collections.singletonList(l.split(": ")[0]), Arrays.asList(l.split(": ")[1].split(" "))).flatMap(x -> x.stream()).collect(Collectors.toList());
            g.addVertex(temp.get(0));
            temp.stream().skip(1).forEach(r -> {
                g.addVertex(r);
                g.addEdge(temp.get(0), r);
            });
        });
        int cache = new StoerWagnerMinimumCut<>(g).minCut().size();
        return String.valueOf((g.vertexSet().size() - cache) * cache);
    }

    @Override
    public String p2() throws IOException {
        return "!!!Merry Christmas!!!";
    }

    public static void main(String[] args) throws IOException{
        RUNDAY.run(new AoC25());

    }
}
