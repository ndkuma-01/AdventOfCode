package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.Cyclones;
import Utilities.aoc22.HyperDuplex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class AoC24 implements DAYID {

    int maxX, maxY;
    HashSet<HyperDuplex> bounds;

    @Override
    public String p1() throws IOException {
        //takes in the input for day 24
        GetInputs input = new GetInputs(22,24);
        //creates an array lists of the custom data type called cyclone
        //to learn more about the cyclone datatype visit Utilities.aoc22.cyclone
        ArrayList<Cyclones> cyclones = new ArrayList<>();
        //the bounds are reset
        bounds = new HashSet<>();
        //the entirety of the input is entered into a single array
        //where each line is a new index on the array
        String[] lines = input.filetoArray();
        for(int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for(int x = 0; x < line.length(); x++) {
                //a switch statement to figure out the bounds and the cyclones
                switch (line.charAt(x)) {
                    //makes use of the HyperDuplex directioning methods
                    case '#' -> bounds.add(new HyperDuplex(x, y));
                    //case '#':   Set<Grid.Pos> neighbors = getNeighbours(position, movedCyclones);
                    case '>' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.RIGHT));
                    //case '>':    Grid.Pos newPos = moveCyclone(pos, direction);
                    case '<' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.LEFT));
                    //case '<': HyperDuplex.LEFT;
                    case 'v' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.DOWN));
                    //case 'v': HyperDuplex.DOWN;

                    case '^' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.UP));
                }
            }
        }
        //the max x and the max y is found
        maxX = bounds.stream().map(x->x.x).max(Integer::compare).get();
        maxY = lines.length - 1;

        //the start and end points are made
        HyperDuplex start = null;
        HyperDuplex end = null;
        for(int x = 0; x <= maxX; x++) {
            if(!bounds.contains(new HyperDuplex(x,0)))
                start = new HyperDuplex(x,0);
            if(!bounds.contains(new HyperDuplex(x,maxY)))
                end = new HyperDuplex(x,maxY);
        }

        //this prevents the simulation from just moving outside of the maze and moving over to the end
        //instead this forces it to go through to the maze
        bounds.add(start.sum(HyperDuplex.UP));
        bounds.add(start.sum(new HyperDuplex(-1,-1)));
        bounds.add(start.sum(new HyperDuplex(1,-1)));
        bounds.add(end.sum(HyperDuplex.DOWN));
        bounds.add(end.sum(new HyperDuplex(-1,1)));
        bounds.add(end.sum(new HyperDuplex(1,1)));

        int min = 0;
        HashSet<HyperDuplex> curReachable = new HashSet<>();
        curReachable.add(start);

        do {
            cyclones = new ArrayList<>(cyclones.stream().map(this::updatecyclone).toList());
            HashSet<HyperDuplex> cyclonesPos = (HashSet<HyperDuplex>) cyclones.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<HyperDuplex> newReachable = new HashSet<>();
            //this will take in the coordinate points of the current reachable locales
            for(HyperDuplex c : curReachable) {
                //this will figure out whether its direct neighbors is either a cyclone or a bounding wall
                for(HyperDuplex adj : c.directNeighbors())
                    //if not then it will add it to the current reachable spots
                    if(!bounds.contains(adj) && !cyclonesPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !cyclonesPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(end));
        return Integer.toString(min);
    }


    public Cyclones updatecyclone(Cyclones old) {
        //this will essentially similate the movement for a cyclone

        HyperDuplex newPos = old.pos.sum(old.facing);
        //if the bound contains the new position then it will have to rewrap itself around the other way
        if(bounds.contains(newPos)) {
            if(old.facing.equals(HyperDuplex.UP)) {
                return new Cyclones(new HyperDuplex(old.pos.x,maxY - 1), old.facing);
            } else if(old.facing.equals(HyperDuplex.DOWN))
                return new Cyclones(new HyperDuplex(old.pos.x,1),old.facing);
            else if(old.facing.equals(HyperDuplex.RIGHT))
                return new Cyclones(new HyperDuplex(1,old.pos.y),old.facing);
            else
                return new Cyclones(new HyperDuplex(maxX - 1, old.pos.y),old.facing);
        } else {
            return new Cyclones(newPos,old.facing);
        }
    }

    @Override
    public String p2() throws IOException {
        //gets the input for day 24
        GetInputs input = new GetInputs(22,24);
        ArrayList<Cyclones> cyclones = new ArrayList<>();
        bounds = new HashSet<>();
        //this is all the same as part 1
        String[] lines = input.filetoArray();
        for(int y = 0; y < lines.length; y++) {
            String line = lines[y];
//            for (int x = 0; x < grid[y].length; x++) {
//                if (grid[y][x] == '>') {
//                    final int xPos = 1 + (x - 1 + minute) % (grid[y].length - 2);
//                    positions.add(new Position(xPos, y));
//                } else if (grid[y][x] == '<') {
//                    final int xPos = 1 + Math.floorMod(x - 1 - minute, grid[y].length - 2);
//                    positions.add(new Position(xPos, y));
//                } else if (grid[y][x] == 'v') {
//                    final int yPos = 1 + (y - 1 + minute) % (grid.length - 2);
//                    positions.add(new Position(x, yPos));
//                } else if (grid[y][x] == '^') {
//                    final int yPos = 1 + Math.floorMod(y - 1 - minute, grid.length - 2);
//                    positions.add(new Position(x, yPos));
//                }
//            }
            for(int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case '#' -> bounds.add(new HyperDuplex(x, y));
                    //case '#':   Set<Grid.Pos> neighbors = getNeighbours(position, movedCyclones);
                    case '>' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.RIGHT));
                    //case '>':    Grid.Pos newPos = moveCyclone(pos, direction);
                    case '<' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.LEFT));
                    //case '<': HyperDuplex.LEFT;
                    case 'v' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.DOWN));
                    //case 'v': HyperDuplex.DOWN;

                    case '^' -> cyclones.add(new Cyclones(new HyperDuplex(x, y), HyperDuplex.UP));
                }
            }
        }

        maxX = bounds.stream().map(x->x.x).max(Integer::compare).get();
        maxY = lines.length - 1;

        HyperDuplex start = null;
        HyperDuplex end = null;

//        for (int row = 0; row < height; row++) {
//            String line = lines[row + 1];
//
//            for (int column = 0; column < width; column++) {
//                tiles[row][column] = Tile.of(line.charAt(column + 1));
//            }
//        }
        for(int x = 0; x <= maxX; x++) {
            if(!bounds.contains(new HyperDuplex(x,0)))
                start = new HyperDuplex(x,0);
            if(!bounds.contains(new HyperDuplex(x,lines.length - 1)))
                end = new HyperDuplex(x,lines.length - 1);
        }

        //makes it so that we have to go through the maze
        bounds.add(start.sum(HyperDuplex.UP));
        bounds.add(start.sum(new HyperDuplex(-1,-1)));
        bounds.add(start.sum(new HyperDuplex(1,-1)));
        bounds.add(end.sum(HyperDuplex.DOWN));
        bounds.add(end.sum(new HyperDuplex(-1,1)));
        bounds.add(end.sum(new HyperDuplex(1,1)));

        int totalTrip = 0;

        //one trip, start to end
        int min = 0;
        HashSet<HyperDuplex> curReachable = new HashSet<>();
        curReachable.add(start);
        do {
            cyclones = new ArrayList<>(cyclones.stream().map(this::updatecyclone).toList());
            HashSet<HyperDuplex> cyclonesPos = (HashSet<HyperDuplex>) cyclones.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<HyperDuplex> newReachable = new HashSet<>();
//            while(!currentPossible.isEmpty()) {
//                if (currentPossible.contains(target)) {
//                    break;
//                }
//                Map<Grid.Pos, List<Character>> movedCyclones = moveCyclones();
//                Set<Grid.Pos> newPossible = new HashSet<>();
//                for (var position : currentPossible) {
//                    Set<Grid.Pos> neighbors = getNeighbours(position, movedCyclones);
//                    newPossible.addAll(neighbors);
//                    if (!movedCyclones.containsKey(position)) {
//                        newPossible.add(position);
//                    }
//                }
//                currentPossible = newPossible;
//                cyclones = movedCyclones;
//                currentMinute++;
//            }
            for(HyperDuplex c : curReachable) {
                for(HyperDuplex adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !cyclonesPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !cyclonesPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(end));

        //next trip, end to start
        totalTrip += min;
        min = 0;
        curReachable = new HashSet<>();
        curReachable.add(end);
        do {
            cyclones = new ArrayList<>(cyclones.stream().map(this::updatecyclone).toList());
            HashSet<HyperDuplex> cyclonesPos = (HashSet<HyperDuplex>) cyclones.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<HyperDuplex> newReachable = new HashSet<>();
//            while(!currentPossible.isEmpty()) {
//                if (currentPossible.contains(target)) {
//                    break;
//                }
//                Map<Grid.Pos, List<Character>> movedCyclones = moveCyclones();
//                Set<Grid.Pos> newPossible = new HashSet<>();
//                for (var position : currentPossible) {
//                    Set<Grid.Pos> neighbors = getNeighbours(position, movedCyclones);
//                    newPossible.addAll(neighbors);
//                    if (!movedCyclones.containsKey(position)) {
//                        newPossible.add(position);
//                    }
//                }
//                currentPossible = newPossible;
//                cyclones = movedCyclones;
//                currentMinute++;
//            }
            for(HyperDuplex c : curReachable) {
                for(HyperDuplex adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !cyclonesPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !cyclonesPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(start));

        totalTrip += min;
        min = 0;
        curReachable = new HashSet<>();
        curReachable.add(start);

        //last trip, start to end again
        do {
            cyclones = new ArrayList<>(cyclones.stream().map(this::updatecyclone).toList());
            HashSet<HyperDuplex> cyclonesPos = (HashSet<HyperDuplex>) cyclones.stream().map(x -> x.pos).collect(Collectors.toSet());
            HashSet<HyperDuplex> newReachable = new HashSet<>();
//            while(!currentPossible.isEmpty()) {
//                if (currentPossible.contains(target)) {
//                    break;
//                }
//                Map<Grid.Pos, List<Character>> movedCyclones = moveCyclones();
//                Set<Grid.Pos> newPossible = new HashSet<>();
//                for (var position : currentPossible) {
//                    Set<Grid.Pos> neighbors = getNeighbours(position, movedCyclones);
//                    newPossible.addAll(neighbors);
//                    if (!movedCyclones.containsKey(position)) {
//                        newPossible.add(position);
//                    }
//                }
//                currentPossible = newPossible;
//                cyclones = movedCyclones;
//                currentMinute++;
//            }
            for(HyperDuplex c : curReachable) {
                for(HyperDuplex adj : c.directNeighbors())
                    if(!bounds.contains(adj) && !cyclonesPos.contains(adj))
                        newReachable.add(adj);
                if(!bounds.contains(c) && !cyclonesPos.contains(c))
                    newReachable.add(c);
            }
            curReachable = newReachable;
            min++;
        } while(!curReachable.contains(end));

        totalTrip += min;
        return Integer.toString(totalTrip);
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC24());
    }
}
