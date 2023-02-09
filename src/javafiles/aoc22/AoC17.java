package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.HyperDuplex;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class AoC17 implements DAYID {



    public boolean containsAny(HashSet<HyperDuplex> big, HashSet<HyperDuplex> small) {
        for(HyperDuplex c : small)
            if(big.contains(c))
                return true;
        return false;
    }

    @Override
    public String p1() throws FileNotFoundException {
        //gets the input for day 17
        GetInputs input = new GetInputs(22,17);
        //the first thing that the program will do is take into account the different shapes of the rocks
        //by making hashsets that contain hyper duplex coordinate points of the shapes
        ArrayList<HashSet<HyperDuplex>> rocks = new ArrayList<>();
        //makes the horizontal line line
        HashSet<HyperDuplex> horizLine = new HashSet<>();
        for(int i = 0; i < 4; i++)
            horizLine.add(new HyperDuplex(i,0));

        rocks.add(horizLine);

        //makes the plus sign
        HashSet<HyperDuplex> plus = new HashSet<>();
        plus.add(new HyperDuplex(0,1));
        plus.add(new HyperDuplex(1,1));
        plus.add(new HyperDuplex(2,1));
        plus.add(new HyperDuplex(1,2));
        plus.add(new HyperDuplex(1,0));
        rocks.add(plus);

        //make the backwards L
        HashSet<HyperDuplex> backL = new HashSet<>();
        backL.add(new HyperDuplex(0,0));
        backL.add(new HyperDuplex(1,0));
        backL.add(new HyperDuplex(2,0));
        backL.add(new HyperDuplex(2,1));
        backL.add(new HyperDuplex(2,2));
        rocks.add(backL);

        //makes the vertical line
        HashSet<HyperDuplex> vertLine = new HashSet<>();
        for(int i = 0; i < 4; i++)
            vertLine.add(new HyperDuplex(0,i));
        rocks.add(vertLine);

        //makes the square
        HashSet<HyperDuplex> square = new HashSet<>();
        square.add(new HyperDuplex(0,0));
        square.add(new HyperDuplex(0,1));
        square.add(new HyperDuplex(1,1));
        square.add(new HyperDuplex(1,0));
        rocks.add(square);

        HashSet<HyperDuplex> rocksInCave = new HashSet<>();
        //this creates the floor of the simulation
        for(int i = 0; i < 7; i++) {
            rocksInCave.add(new HyperDuplex(i,-1));
        }
        LinkedList<Boolean> pushRight = new LinkedList<>();
        //this will go through each character and find the boolean value of whether the current character we are looking at 
        // is a push to the right
        for(char c : input.nextLine().trim().toCharArray())
            pushRight.add(c == '>');
//        int topRowToDraw = calculateHighestRowToDraw();
//        for (int row = topRowToDraw; row >= 0; row--) {
//            result.append(renderRow(row));
//        }
//        result.append("+-------+\n");
//
//        return result.toString();


        //for each rock, x=0 is the leftmost point and y=0 is the bottommost point

        int cnt = 0;
        for(int j = 0; j < 2022; j++) {
            HashSet<HyperDuplex> currentRock = new HashSet<>(rocks.get(j % 5));

            int max = rocksInCave.stream().map(x -> x.y).max(Integer::compare).get();

            //offset rock to proper starting position
            currentRock = (HashSet<HyperDuplex>) currentRock.stream().map(x -> x.sum(new HyperDuplex(2, max + 4))).collect(Collectors.toSet());
            movement:
            while(true) {
                //trys to push the rocks to the right
                if(pushRight.get(cnt % pushRight.size())) {
                    int highestX = currentRock.stream().map(x -> x.x).max(Integer::compare).get();
                    HashSet<HyperDuplex> placeHolderRight = (HashSet<HyperDuplex>) currentRock.stream().map(x -> x.sum(HyperDuplex.RIGHT)).collect(Collectors.toSet());
                    if(highestX < 6 && !containsAny(rocksInCave,placeHolderRight)) {
                        //pushes entirely to the right
                        currentRock = placeHolderRight;
                    }
                } else {
                    int lowestX = currentRock.stream().map(x -> x.x).min(Integer::compare).get();
                    HashSet<HyperDuplex> tentativeLeft = (HashSet<HyperDuplex>) currentRock.stream().map(x -> x.sum(HyperDuplex.LEFT)).collect(Collectors.toSet());
                    if(lowestX > 0 && !containsAny(rocksInCave,tentativeLeft))
                        currentRock = tentativeLeft;
                }
                cnt++;

                //this will check if the current rock is currently resting on something
                for(HyperDuplex c : currentRock) {
                    if(rocksInCave.contains(c.sum(HyperDuplex.UP))) {
                        //turns the rock into a solid object that won't move anymore
                        rocksInCave.addAll(currentRock);
                        break movement;
                    }
                }

                //if not resting on something, shift entire rock down
                currentRock = (HashSet<HyperDuplex>) currentRock.stream().map(x -> x.sum(HyperDuplex.UP)).collect(Collectors.toSet());

            }
        }

        return Integer.toString(rocksInCave.stream().map(x -> x.y).max(Integer::compare).get() + 1);
    }


    //the solution I made for the second part is essentially completely different then my solution to the first part
    //this is because the solution I used for the first part wasn't going to be easily implementable for the second part
    //hence I developed another solution that heavily makes use of a rock class, and a simulation state record
    private static HashMap<Long,Integer> rows = new HashMap<>();
    private static int[] bitmask;

    //a bit shift is used
    //this was inspired by the solution made by hyperneutrino and by jonathan paulson
    //however other than that this code goes about solving the problem in a unique way
    static {
        bitmask = new int[8];
        for (int i = 0; i < 8; i++) {
            bitmask[i] = 1 << i;
        }
    }
    @Override
    public String p2() throws FileNotFoundException {
        //takes in the input for day 17
        GetInputs input = new GetInputs(22,17);
        String jets = input.nextLine();
        int jetLen = jets.length();
        //this makes a list of rocks and these boolean values represent each of the different shapes of the rocks
        List<Rock> rocks = new ArrayList<>();
        //horizontal line
        rocks.add(new Rock(new boolean[][]{{true, true, true, true}}));
        //cross
        rocks.add(new Rock(new boolean[][]{{false, true, false}, {true, true, true}, {false, true, false}}));
        //backwards L
        rocks.add(new Rock(new boolean[][]{{false, false, true}, {false, false, true}, {true, true, true}}));
        //vertical line
        rocks.add(new Rock(new boolean[][]{{true}, {true}, {true}, {true}}));
        //square
        rocks.add(new Rock(new boolean[][]{{true, true}, {true, true}}));

        long maxheight = 0;
        int rockIdx = 0;
        int jetIdx = 0;
        //this will find out how long the period lasted and also record where the resting places are in the simulation
        HashMap<getStateIdx, List<SS>> awareness = new HashMap<>();
        long goal = 1000000000000L;
        long additionalCycles = -1;
        long diffPerCycle = -1;
        for (long round = 0; round < goal; round++) {
            boolean debug = false;
            Rock cur = rocks.get(rockIdx);
            rockIdx = (rockIdx + 1) % 5;
            long y = maxheight + 3 + cur.height();
//            //horizontal line
//            HashSet<Coord> horizLine = new HashSet<>();
//            for(int i = 0; i < 4; i++)
//                horizLine.add(new Coord(i,0));
//
//            rocks.add(horizLine);
//
//            //plus sign
//            HashSet<Coord> plus = new HashSet<>();
//            plus.add(new Coord(0,1));
//            plus.add(new Coord(1,1));
//            plus.add(new Coord(2,1));
//            plus.add(new Coord(1,2));
//            plus.add(new Coord(1,0));
//            rocks.add(plus);
//
//            //backwards L
//            HashSet<Coord> backL = new HashSet<>();
//            backL.add(new Coord(0,0));
//            backL.add(new Coord(1,0));
//            backL.add(new Coord(2,0));
//            backL.add(new Coord(2,1));
//            backL.add(new Coord(2,2));
//            rocks.add(backL);
//
//            //vertical line
//            HashSet<Coord> vertLine = new HashSet<>();
//            for(int i = 0; i < 4; i++)
//                vertLine.add(new Coord(0,i));
//            rocks.add(vertLine);
//
//            //square
//            HashSet<Coord> square = new HashSet<>();
//            square.add(new Coord(0,0));
//            square.add(new Coord(0,1));
//            square.add(new Coord(1,1));
//            square.add(new Coord(1,0));
//            rocks.add(square);
            int x = 2;
            while (true) {
                char dir = jets.charAt(jetIdx);
                jetIdx = (jetIdx + 1) % jetLen;
                if (dir == '<') {
                    x = cur.moveLeft(x, y);
                } else {
                    x = cur.moveRight(x, y);
                }
                if (cur.canMoveDown(x, y)) {
                    y--;
                } else {
                    cur.block(x, y);
                    maxheight = Math.max(maxheight, y);
                    if (additionalCycles == -1 && rockIdx == 0) {
                        getStateIdx is = new getStateIdx(jetIdx, rockIdx);
                        List<SS> sim = awareness.get(is);
                        if (sim == null) {
                            sim = new ArrayList<SS>();
                            awareness.put(is, sim);
                        } else if (sim.size() > 1) {
//                            if (debug) {
//                                long ly = y;
//                                long lr = round;
//                                for (int i = sim.size() - 1; i >= 0; i--) {
//                                    SS s = sim.get(i);
//                                    ly = s.y;
//                                    lr = s.round;
//                                }
//                            }
//                            while(true) {
//                                //try to push with jet
//                                if(jetPattern.get(jetCounter % jetPattern.size())) {
//                                    int highestX = curRock.stream().map(x -> x.x).max(Integer::compare).get();
//                                    HashSet<Coord> tentativeRight = (HashSet<Coord>) curRock.stream().map(x -> x.sum(Coord.RIGHT)).collect(Collectors.toSet());
//                                    if(highestX < 6 && !containsAny(rocksInCave,tentativeRight)) {
//                                        //push entire to right
//                                        curRock = tentativeRight;
//                                    }
//                                } else {
//                                    int lowestX = curRock.stream().map(x -> x.x).min(Integer::compare).get();
//                                    HashSet<Coord> tentativeLeft = (HashSet<Coord>) curRock.stream().map(x -> x.sum(Coord.LEFT)).collect(Collectors.toSet());
//                                    if(lowestX > 0 && !containsAny(rocksInCave,tentativeLeft))
//                                        curRock = tentativeLeft;
//                                }
//                                jetCounter++;
                            SS last = sim.get(sim.size() - 1);
                            for (int i = sim.size() - 2; i >= 0; i--) {
                                SS cand = sim.get(i);
//                                    int curHeight = rocksInCave.stream().map(x->x.y).max(Integer::compare).get();
//                                    //create cache key for current rock state
//                                    HashSet<Coord> cacheKey = convertToCacheKey(rocksInCave);
//                                    if(!cycleFound && cache.containsKey(cacheKey)) {
//                                        //get info about cycle
//                                        Coord info = cache.get(cacheKey);
                                if (equal(cand, last)) {
                                    long period = last.round - cand.round;
                                    diffPerCycle = last.y - cand.y;

//                                        int oldTime = info.x;
//                                        int oldHeight = info.y;
//                                        int cycleLength = (int) (rockCount - oldTime);
//                                        int cycleHeightChange = curHeight - oldHeight;
                                    additionalCycles = (goal - round) / period;
                                    round += additionalCycles * period;
                                }
                            }
                        }
                        sim.add(new SS(round, y));
//
//                        long numCycles = (LENGTH - rockCount) / cycleLength;
//
//                        heightFromCycleRepeat = cycleHeightChange * numCycles;
//                        rockCount += numCycles * cycleLength;
//
//                        cycleFound = true;
                    }
                    break;
                }
            }
        }
        long pt2 = maxheight + additionalCycles * diffPerCycle;
        return String.valueOf(pt2);

    }
    static boolean isBlocked(int x, long y) {
        Integer cur = rows.get(y);
        if (cur == null) {
            return false;
        }
        return (cur & bitmask[x]) != 0;
    }

    static void addBlocked(int x, long y) {
        Integer cur = rows.get(y);
        if (cur == null) {
            cur = bitmask[x];
        } else {
            cur = cur.intValue() | bitmask[x];
        }
        rows.put(y, cur);
    }

    private static boolean equal(SS one, SS two) {
        long diff = two.round - one.round;
        if (one.round < diff) {
            return false;
        }
        long r1 = one.y;
        long r2 = two.y;
        for (long r = 0; r < diff; r++) {
            for (int i = 0; i < 7; i++) {
                if (isBlocked(i, r1) != isBlocked(i, r2)) {
                    return false;
                }
            }
            r1--;
            r2--;
        }
        return true;
    }
// USED FOR DEBUGGING
//    public static void print(String s, long height) {
//        System.out.println(s);
//        while (height > 0) {
//            System.out.print("|");
//            for (int i = 0; i < 7; i++) {
//                if (isBlocked(i, height)) {
//                    System.out.print("#");
//                } else {
//                    System.out.print(".");
//                }
//            }
//            System.out.println("|");
//            height--;
//        }
//        System.out.println("+-------+\n");
//    }

    record getStateIdx(int jetIdx, int rockIdx) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof getStateIdx state)) return false;

            if (jetIdx != state.jetIdx) return false;
            return rockIdx == state.rockIdx;
        }

        @Override
        public int hashCode() {
            int result = jetIdx;
            result = 31 * result + rockIdx;
            return result;
        }
    }
    record SS(long round, long y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SS that)) return false;

            if (round != that.round) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = (int) (round ^ (round >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            return result;
        }
    }

    static class Rock {
        boolean[][] shape;

        public Rock(boolean[][] shape) {
            this.shape = shape;
        }

        public int height() {
            return shape.length;
        }

        public int moveLeft(int x, long y) {
            if (x == 0) {
                return 0;
            }
            for (boolean[] row : shape) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] && isBlocked(x + i - 1, y)) {
                        return x;
                    }
                }
                y--;
            }
            return x - 1;
        }

        public int moveRight(int x, long y) {
            if (x + shape[0].length == 7) {
                return x;
            }
            for (boolean[] row : shape) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] && isBlocked(x + i + 1, y)) {
                        return x;
                    }
                }
                y--;
            }
            return x + 1;
        }

        public boolean canMoveDown(int x, long y) {
            if (y - height() == 0) {
                return false;
            }
            y--;
            for (boolean[] row : shape) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] && isBlocked(x + i, y)) {
                        return false;
                    }
                }
                y--;
            }
            return true;
        }

        public void block(int x, long y) {
            for (boolean[] row : shape) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i]) {
                        addBlocked(x + i, y);
                    }
                }
                y--;
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC17());
    }
}
