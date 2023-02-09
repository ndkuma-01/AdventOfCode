package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AoC23 implements DAYID {
    //used a classic enum for directions
    enum Direction {
        N(0, -1), S(0, 1), W(-1, 0), E(1, 0);

        final int x;
        final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        List<Pos> getPotentialBlockers(Pos pos) {
            //this will take a list of positions and look around at all of hte directions to find where the plockers are
            List<Pos> retain = new LinkedList<>();
            Pos one = new Pos(pos.x + x, pos.y + y);
            retain.add(one);
            if (this == N || this == S) {
                retain.add(new Pos(one.x + W.x, one.y + W.y));
                retain.add(new Pos(one.x + E.x, one.y + E.y));
            } else {
                retain.add(new Pos(one.x + N.x, one.y + N.y));
                retain.add(new Pos(one.x + S.x, one.y + S.y));
            }
            return retain;
        }
    }

    @Override
    public String p1() throws FileNotFoundException {
        //gets the inputs from day 23
        GetInputs inputs = new GetInputs(22,23);
        String s;
        //this contains a hashmap of all the positions and the elf they have in them
        HashMap<Pos, Elf> elves = new HashMap<>();
        int y = 0;
        //this simple parses the input
        while (inputs.hasLines()){
             s = inputs.nextLine();
            for (int x = 0; x < s.length(); x++) {
                if (s.charAt(x) == '#') {
                    elves.put(new Pos(x, y), new Elf());
                }
            }
            y++;
        }
//        for(int iter = 0; iter < 10; iter++) {
//            //first half
//            HashMap<Coord,Coord> proposals = new HashMap<>();
//            for(Coord elf : elves) {
//                //get list of adjacent positions, keep ones that contain elves
//                ArrayList<Coord> adj = elf.allNeighbors();
//                adj.retainAll(elves);
//                //if no elves nearby, no movement
//                if(adj.size() == 0)
//                    continue;
//                }
//            }
        //a linked list is made of all the directions that will allow for easy manipulation later
        LinkedList<Direction> directions = new LinkedList<>(List.of(Direction.N, Direction.S, Direction.W, Direction.E));

        // this will go ahead and simulate the movement
        long pt1 = 0;
        HashMap<Pos, List<Elf>> proposed = new HashMap<>();
        //we create a hashmap of proposed moves
        int round;
        for (round = 0; ; round++) {
            // compute the proposed moves
            proposed.clear();
            for (var cur : elves.entrySet()) {
                Pos pos = cur.getKey();
                Elf elf = cur.getValue();
//                for(Coord c : dirs) {
//                    ArrayList<Coord> checks = new ArrayList<>();
//                    checks.add(elf.sum(c));
//                    checks.addAll(diags(c).stream().map(elf::sum).toList());
//                    checks.retainAll(adj);
//                    if(checks.size() == 0) {
//                        proposals.put(elf,elf.sum(c));
//                        break;
//                    }
                Pos propCoord = elf.calculateProposed(pos, elves, directions);
                if (propCoord != null) {
                    proposed.computeIfAbsent(propCoord, k -> new LinkedList<>());
                    proposed.get(propCoord).add(elf);
                }
            }

            // if there are any clashes between moves than we eliminate those
            boolean anyoneMoved = false;
            for (var entry : proposed.entrySet()) {
                Pos coord = entry.getKey();
                List<Elf> list = entry.getValue();
                if (list.size() == 1) {
                    Elf elf = list.get(0);
                    Elf removed = elves.remove(elf.pos);
                    assert elf == removed;
                    elves.put(coord, elf);
                    anyoneMoved = true;
                }
            }
            //one it is round 9 we then finish and use a countempty value
            if (round == 9) {
                // part 1 finished
                int c = countEmpty(elves,"");
                return String.valueOf(c);
            }
            if (!anyoneMoved) {
                break;
            }
            directions.addLast(directions.removeFirst());
        }
        return "";
    }

    @Override
    public String p2() throws FileNotFoundException {
        //get inputs from day 23
        GetInputs inputs = new GetInputs(22,23);
        String s;
        HashMap<Pos, Elf> elves = new HashMap<>();
        //a hashmap of where the elves are
        int y = 0;
        //parses the input and takes into account the coordinate points of the elves
        while (inputs.hasLines()){
            s = inputs.nextLine();
            for (int x = 0; x < s.length(); x++) {
                if (s.charAt(x) == '#') {
                    elves.put(new Pos(x, y), new Elf());
                }
            }
            y++;
        }
        //creates a linked list of the directions from the enum in order for easy manipulation later on
        LinkedList<Direction> directions = new LinkedList<>(List.of(Direction.N, Direction.S, Direction.W, Direction.E));

        // simulate the roudn
        long pt1 = 0;
        HashMap<Pos, List<Elf>> proposed = new HashMap<>();
        int round;
        for (round = 0; ; round++) {
            // figure out what point each elf is proposing to move into
            proposed.clear();
//            for(Coord c : dirs) {
//                ArrayList<Coord> checks = new ArrayList<>();
//                checks.add(elf.sum(c));
//                checks.addAll(diags(c).stream().map(elf::sum).toList());
//                checks.retainAll(adj);
//                if(checks.size() == 0) {
//                    proposals.put(elf,elf.sum(c));
//                    break;
//                }
            for (var entry : elves.entrySet()) {
                Pos pos = entry.getKey();
                Elf elf = entry.getValue();
                Pos proposedPos = elf.calculateProposed(pos, elves, directions);
                if (proposedPos != null) {
                    proposed.computeIfAbsent(proposedPos, k -> new LinkedList<>());
                    proposed.get(proposedPos).add(elf);
                }
            }

            // get rid of any clashing moves
            boolean anyoneMoved = false;
            for (var entry : proposed.entrySet()) {
                Pos pos = entry.getKey();
                List<Elf> list = entry.getValue();
                if (list.size() == 1) {
                    Elf elf = list.get(0);
                    Elf removed = elves.remove(elf.pos);
//                    HashSet<Coord> newElves = new HashSet<>();
//                    HashMap<Coord,Long> propFreq = new HashMap<>(proposals.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
//                    for(Coord elf : elves) {
//                        if(propFreq.getOrDefault(proposals.getOrDefault(elf,null),2L) > 1L)
//                            newElves.add(elf);
//                        else
//                            newElves.add(proposals.get(elf));
//                    }
                    assert elf == removed;
                    elves.put(pos, elf);
                    anyoneMoved = true;
                }
            }

            if (!anyoneMoved) {
                // part 2 finished
                break;
            }
            directions.addLast(directions.removeFirst());
        }
        return String.valueOf(round+1);

    }

    private static int countEmpty(HashMap<Pos, Elf> elves, String print) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (var entry : elves.entrySet()) {
            Pos pos = entry.getKey();
            minX = Math.min(minX, pos.x);
            maxX = Math.max(maxX, pos.x);
            minY = Math.min(minY, pos.y);
            maxY = Math.max(maxY, pos.y);
            //finds the bounding box
        }
//        if (print != null) {
//            System.out.println(print + ": minX = " + minX + "; maxX = " + maxX + "; minY = " + minY + "; maxY = " + maxY);
//        }
        //goes through and counts the elfs
        int count = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                boolean elf = elves.containsKey(new Pos(x, y));
                if (print != null) {
//                    System.out.print(elf ? '#' : '.');
                }
                if (!elf) {
                    count++;
                }
            }
            if (print != null) {
//                System.out.println();
            }
        }
        return count;
    }




    record Pos(int x, int y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pos pos)) return false;

            if (x != pos.x) return false;
            return y == pos.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    static class Elf {
        Pos pos;

        public Pos calculateProposed(Pos pos, HashMap<Pos, Elf> elves, List<Direction> directions) {
            this.pos = pos;
            boolean anotherElfFound = false;
            Direction proposed = null;
            for (Direction d : directions) {
                List<Pos> potential = d.getPotentialBlockers(pos);
                boolean blocked = potential.stream().anyMatch(elves::containsKey);
                anotherElfFound = anotherElfFound || blocked;
                if (!blocked && proposed == null) {
                    proposed = d;
                }
            }
            if (anotherElfFound && proposed != null) {
                return new Pos(pos.x + proposed.x, pos.y + proposed.y);
            } else {
                return null;
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC23());
    }
}
