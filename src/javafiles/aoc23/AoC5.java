package javafiles.aoc23;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AoC5 implements DAYID {
    List<String> lines = new ArrayList<>();
    List<Long> seeds = new ArrayList<Long>();

    public ArrayList<ArrayList<Long>> parse(int beginI, int endI) {
        ArrayList<ArrayList<Long>> res = new ArrayList<>();
        for (int i = beginI; i < endI; i++) {
            String line = lines.get(i);
            List<Long> temp = Arrays.stream(line.split(" ")).map(x -> x.trim()).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
            res.add((ArrayList<Long>) temp);
        }
        return res;
    }

    public void convert(ArrayList<ArrayList<Long>> curr) {
        for (int j = 0; j < seeds.size(); j++) {
            boolean found = false;
            Long currSeed = seeds.get(j);
            for (int i = 0; i < curr.size(); i++) {
                if (curr.get(i).get(1) <= currSeed && currSeed <= curr.get(i).get(1) + curr.get(i).get(2)) {
                    seeds.set(j, (currSeed - curr.get(i).get(1) + curr.get(i).get(0)));
                }
            }
        }
    }



    @Override
    public String p1() throws IOException {
        GetInputs in = new GetInputs(23, 5);
        ArrayList<ArrayList<ArrayList<Long>>> maps = new ArrayList<>();
        String[] strMaps = {"seed-to-soil map:", "soil-to-fertilizer map:", "fertilizer-to-water map:", "water-to-light map:", "light-to-temperature map:", "temperature-to-humidity map:", "humidity-to-location map:"};
        ArrayList<Integer> blanks = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("C:\\Users\\nkh15448\\IdeaProjects\\AdventOfCode\\src\\inputs\\twothree\\input5.txt"));
        } catch (Exception e) {
        }
        blanks = (ArrayList<Integer>) IntStream.range(0, lines.size()).filter(i -> lines.get(i).equals("")).boxed().collect(Collectors.toList());
        seeds = Arrays.asList(lines.get(0).split(":")[1].trim().split(" ")).stream().map(String::trim).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
        for (int i = 0; i < strMaps.length; i++) {
            if (i != strMaps.length - 1) {
                maps.add(parse(lines.indexOf(strMaps[i]) + 1, blanks.get(i + 1)));
            } else {
                maps.add(parse(lines.indexOf(strMaps[i]) + 1, lines.size()));
            }
            convert(maps.get(i));
        }
        return String.valueOf(seeds.stream().mapToLong(Long::valueOf).min().getAsLong());
    }


    @Override
    public String p2() throws IOException {
        GetInputs in = new GetInputs(23, 5);
        ArrayList<ArrayList<ArrayList<Long>>> maps = new ArrayList<>();
        String[] strMaps = {"seed-to-soil map:", "soil-to-fertilizer map:", "fertilizer-to-water map:", "water-to-light map:", "light-to-temperature map:", "temperature-to-humidity map:", "humidity-to-location map:"};
        ArrayList<Integer> blanks = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("C:\\Users\\nkh15448\\IdeaProjects\\AdventOfCode\\src\\inputs\\twothree\\input5.txt"));
        } catch (Exception e) {
        }
        blanks = (ArrayList<Integer>) IntStream.range(0, lines.size()).filter(i -> lines.get(i).equals("")).boxed().collect(Collectors.toList());
        seeds = Arrays.asList(lines.get(0).split(":")[1].trim().split(" ")).stream().map(String::trim).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
        ArrayList<ArrayList<Long>> seedPairs = new ArrayList<>();
        IntStream.range(0, (seeds.size()) - 1).filter(i -> i%2==0).mapToObj(i-> new ArrayList(List.of(seeds.get(i), seeds.get(i+1)))).forEach(seedPairs::add);
        for (int i = 0; i < strMaps.length; i++) {
            if ((i != strMaps.length - 1)) {
                maps.add(parse(lines.indexOf(strMaps[i]) + 1, blanks.get(i + 1)));
            } else {
                maps.add(parse(lines.indexOf(strMaps[i]) + 1, lines.size()));
            }
        }
        ArrayList<Long> res = new ArrayList<>();
        for (int i = 0; i < seedPairs.size(); i++) {
            ArrayList<ArrayList<Long>> ranges = new ArrayList<>();
            ranges.add(new ArrayList<>(List.of(seedPairs.get(i).get(0), (seedPairs.get(i).get(0) + seedPairs.get(i).get(1)))));
            for (int j = 0; j < maps.size(); j++) {
                ranges = convert2(maps.get(j), ranges);
            }
            ArrayList<ArrayList<Long>> temp = new ArrayList<>();
            ArrayList<Long> min = new ArrayList<>(List.of(Long.MAX_VALUE, Long.MAX_VALUE));
            for (int j = 0; j < ranges.size(); j++) {
                if (ranges.get(j).get(0) < min.get(0)) {
                    min.set(0, ranges.get(j).get(0));
                    min.set(1, ranges.get(j).get(1));
                } else if (ranges.get(j).get(0) == min.get(0)) {
                    if (ranges.get(j).get(1) < min.get(1)) {
                        min.set(0, ranges.get(j).get(0));
                        min.set(1,ranges.get(j).get(1));
                    }
                }
            }
            res.add(min.get(0));
        }
        return String.valueOf(res.stream().mapToLong(Long::valueOf).min().getAsLong());
    }
    public ArrayList<ArrayList<Long>> convert2(ArrayList<ArrayList<Long>> map, ArrayList<ArrayList<Long>> ranges) {
        ArrayList<ArrayList<Long>> withinRange = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            Long dest = map.get(i).get(0);
            Long start = map.get(i).get(1);
            Long sz = map.get(i).get(2);
            Long src_end = start + sz;
            ArrayList<ArrayList<Long>> notMapped = new ArrayList<>();
            while (ranges.size() != 0) {
                ArrayList<Long> temp = ranges.remove(ranges.size() - 1);
                Long ed = temp.get(1);
                if (Math.min(ed,start) > temp.get(0)) {
                    notMapped.add(new ArrayList<>(List.of(temp.get(0),Math.min(ed,start))));
                }
                if (ed > Math.max(src_end, temp.get(0))) {
                    notMapped.add(new ArrayList<>(List.of(Math.max(src_end,temp.get(0)),ed)));
                }
                if (Math.min(src_end, ed) > Math.max(temp.get(0), start)) {
                    withinRange.add(new ArrayList<Long>(List.of(Math.max(temp.get(0), start) - start + dest, Math.min(src_end, ed) - start + dest)));
                }
            }
            ranges = notMapped;
        }
        ArrayList<ArrayList<Long>> res = new ArrayList<>();
        IntStream.range(0, withinRange.size()).mapToObj(withinRange::get).forEach(res::add);
        IntStream.range(0, ranges.size()).mapToObj(ranges::get).forEach(res::add);
        return res;
    }

    public static void main(String[] args) throws  IOException{
        RUNDAY.run(new AoC5());
    }
}
