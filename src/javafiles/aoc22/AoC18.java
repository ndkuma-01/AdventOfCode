package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.util.*;

public class AoC18 implements DAYID {
    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;


    @Override
    public String p1() throws FileNotFoundException {
        //gets the input for day 18
        GetInputs input = new GetInputs(22,18);
        String s;
        //makes a list of cube data types called cubes
        //this will hold all of the cubes that are made
        List<Cube> cubes = new ArrayList<>();
        HashMap<Cube, Cube> set = new HashMap<>();
        while (input.hasLines()) {
            s = input.nextLine();
            Cube c = new Cube(s.split(","));
            cubes.add(c);
            set.put(c, c);
        }
//        cubes = new ArrayList<>(cubes);
//        minX = cubes.stream().mapToInt(Cube::getX).min().getAsInt();
//        final int maxX = cubes.stream().mapToInt(Cube::getX).max().getAsInt();
//        minY = cubes.stream().mapToInt(Cube::getY).min().getAsInt();
//        final int maxY = cubes.stream().mapToInt(Cube::getY).max().getAsInt();
//        minZ = cubes.stream().mapToInt(Cube::getZ).min().getAsInt();
//        final int maxZ = cubes.stream().mapToInt(Cube::getZ).max().getAsInt();
//        lavaDrop = new Thing[maxX - minX + 3][maxY - minY + 3][maxZ - minZ + 3];
//        for (int x = 0; x < lavaDrop.length; x++)
//        {
//            for (int y = 0; y < lavaDrop[0].length; y++)
//            {
//                for (int z = 0; z < lavaDrop[0][0].length; z++)
//                {
//                    lavaDrop[x][y][z] = Thing.AIR;
//                }
//            }
//        }
        for (Cube c : cubes) {
            //this will take in the cubes and find their neighbors and find their mins and maxes
            c.getNeighbors(set);
            min = Math.min(min, c.x);
            min = Math.min(min, c.y);
            min = Math.min(min, c.z);
            max = Math.max(max, c.x);
            max = Math.max(max, c.y);
            max = Math.max(max, c.z);
        }
        min--;
        max++;

        int pt1 = 0;
        //finds the unconnected ones
        for (Cube c : cubes) {
            pt1 += c.getLonely();
        }
        return String.valueOf(pt1);
    }
    //simply used for translations later on in the second problem
    record Offset(int idx, int x, int y, int z) {
    }
    static Offset[] offsets = new Offset[]{
            //this would be responsible for checking the neighboring positions by making offsets to them
            new Offset(0, -1, 0, 0),
            new Offset(1, 1, 0, 0),
            new Offset(2, 0, -1, 0),
            new Offset(3, 0, 1, 0),
            new Offset(4, 0, 0, -1),
            new Offset(5, 0, 0, 1),
    };
    //this cube class is a simple class that holds the coordinate points and the neighbors
    static class Cube {
        int x;
        int y;
        int z;
        Cube[] neighbors = new Cube[6];

        public Cube(String[] in) {
            this.x = Integer.parseInt(in[0]);
            this.y = Integer.parseInt(in[1]);
            this.z = Integer.parseInt(in[2]);
        }

        public Cube() {
        }

        public void getNeighbors(HashMap<Cube, Cube> set) {
            //will use the offsets in order to find the all of the neighbors
            Cube tst = new Cube();
            for (Offset o : offsets) {
                tst.x = x + o.x;
                tst.y = y + o.y;
                tst.z = z + o.z;
                Cube c = set.get(tst);
                if (c != null) {
                    neighbors[o.idx] = c;
                }
            }
        }

        public int getLonely() {
            int sum = 0;
            for (Cube c : neighbors) {
                if (c == null) {
                    sum++;
                }
            }
            return sum;
        }
        //simple boolean for finding whether the cube is in bounds or not
        public boolean inBounds() {
            return x >= min && x <= max && y >= min && y <= max && z >= min && z <= max;
        }

        public String toString() {
            return x + "," + y + "," + z;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Cube cube)) {
                return false;
            }

            if (x != cube.x){
                return false;
            }
            if (y != cube.y) {
                return false;
            }
            return z == cube.z;
        }

        public int hashCode() {
            int result = x;
            result *= 31 + y;
            result *= 31 + z;
            return result;
        }
    }

    @Override
    public String p2() throws FileNotFoundException {
        GetInputs input = new GetInputs(22,18);
        String s;
        List<Cube> cubes = new ArrayList<>();
        HashMap<Cube, Cube> set = new HashMap<>();
        while (input.hasLines()) {
            s = input.nextLine();
            Cube c = new Cube(s.split(","));
            cubes.add(c);
            set.put(c, c);
        }
        //        cubes = new ArrayList<>(cubes);
//        minX = cubes.stream().mapToInt(Cube::getX).min().getAsInt();
//        final int maxX = cubes.stream().mapToInt(Cube::getX).max().getAsInt();
//        minY = cubes.stream().mapToInt(Cube::getY).min().getAsInt();
//        final int maxY = cubes.stream().mapToInt(Cube::getY).max().getAsInt();
//        minZ = cubes.stream().mapToInt(Cube::getZ).min().getAsInt();
//        final int maxZ = cubes.stream().mapToInt(Cube::getZ).max().getAsInt();
//        lavaDrop = new Thing[maxX - minX + 3][maxY - minY + 3][maxZ - minZ + 3];
//        for (int x = 0; x < lavaDrop.length; x++)
//        {
//            for (int y = 0; y < lavaDrop[0].length; y++)
//            {
//                for (int z = 0; z < lavaDrop[0][0].length; z++)
//                {
//                    lavaDrop[x][y][z] = Thing.AIR;
//                }
//            }
//        }
        for (Cube c : cubes) {
            c.getNeighbors(set);
            min = Math.min(min, c.x);
            min = Math.min(min, c.y);
            min = Math.min(min, c.z);
            max = Math.max(max, c.x);
            max = Math.max(max, c.y);
            max = Math.max(max, c.z);
        }
        min--;
        max++;

//        int touchingFaces = 0;
//        for (int i = 0; i < cubes.size(); i++)
//        {
//            for (int j = i+1; j < cubes.size(); j++)
//            {
//                if (cubes.get(i).isAdjacent(cubes.get(j)))
//                {
//                    touchingFaces += 1;
//                }
//            }
//        }

        Cube start = new Cube();
        start.x = min;
        start.y = min;
        start.z = min;
        HashSet<Cube> visited = new HashSet<>();
        //the queue data type built into java could have been used to make this more efficient, however this was coded
        //before I was aware of the queue system built into java
        LinkedList<Cube> dequeue = new LinkedList<>();
        dequeue.add(start);
        int pt2 = 0;
        while (!dequeue.isEmpty()) {
            Cube cur = dequeue.removeLast();
            if (visited.contains(cur)) {
                continue;
            }
            visited.add(cur);
            for (Offset o : offsets) {
                Cube tst = new Cube();
                tst.x = cur.x + o.x;
                tst.y = cur.y + o.y;
                tst.z = cur.z + o.z;
                if (!tst.inBounds()) {
                    continue;
                }
//                for (final Cube next : current.getAdjacentCubes())
//                {
//                    if (!visited.contains(next)
//                            && !airAddedToStack.contains(next)
//                            && next.getX() >= 0 && next.getY() >= 0 && next.getZ() >= 0
//                            && next.getX() < lavaDrop.length
//                            && next.getY() < lavaDrop[0].length
//                            && next.getZ() < lavaDrop[0][0].length)
//                    {
//                        if (lavaDrop[next.getX()][next.getY()][next.getZ()] == Thing.AIR) airAddedToStack.add(next);
//                        stack.push(next);
//                    }
//                }
                if (set.containsKey(tst)) {
                    pt2++;
                } else if (!visited.contains(tst)) {
                    dequeue.add(tst);
                }
            }
        }
        return String.valueOf(pt2);
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC18());
    }
}
