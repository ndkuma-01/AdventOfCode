package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.HyperDuplex;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class AoC12 implements DAYID {
    static HashMap<HyperDuplex, Integer> heightMap;
    //this solution uses a custom data type called HyperDuplexes
    //this is a very advanced, versatile, and feature rich coordinate point class made by Nikhil K.
    //feel free to look here: Utilities.aoc22.HyperDuplex, to see more of the features of hyperduplexes


    //this method is a Breadth First Search Pathfinding method created by Nikhil Kumar and is conglomeration
    //of multiple methods found throughout multiple tutorials and articles on BFS pathfinding systems
    public int pathfind(HyperDuplex start, HyperDuplex end) {
        //creates a hashmap for each cost of every coordinate point
        HashMap<HyperDuplex,Integer> nodeCosts = new HashMap<>();
        //creates a linked list that will serve as a queue
        //a built in java data type for queues could have been used to make this process a tad smoother
        //however this alternative completely works and isn't all too bad of an alternative
        LinkedList<HyperDuplex> deQueue = new LinkedList<>();
        //puts in the start into both the node costs and the queue
        nodeCosts.put(start,0);
        deQueue.add(start);
        //goes through the whole entirety of the queue
        //entering in new points to the queue and exiting out old points out of the queue
        while(deQueue.size() > 0) {
            //takes in the current node
            HyperDuplex node = deQueue.poll();
            //if the node that we found is the end point then that means we have found the end of the
            //pathfinding system
            if(node.equals(end)) {
                return nodeCosts.get(end);
            }
//            while (!queue.isEmpty()) {
//                Vertex current = queue.poll();
//                for (int[] direction : directions) {
//                    int nextX = Math.max(current.x + direction[1], 0);
//                    int nextY = Math.max(current.y + direction[0], 0);
//                    if (nextX < mapWidth && nextY < mapHeight) {
//                        Vertex next = new Vertex(nextX, nextY, current.dist + 1, map[nextY][nextX]);
//                        if (!visited.contains(next) && (next.height - current.height <= 1 || current.height == 'S')) {
//                            if (next.height == endMark) {
//                                map[next.y][next.x] = nextTraversableMarkAfter_z;
//                                continue;
//                            }
//                            if (next.height == nextTraversableMarkAfter_z) {
//                                map[next.y][next.x] = endMark;
//                                return next;
//                            }
//                            queue.add(next);
//                            visited.add(next);
//                        }
//                    }
//                }
            //takes in each of the neighbors around the node
            for(HyperDuplex c : node.directNeighbors()) {
                //skips this neighbor if it ouside the bounds of the box or if its height is greater than the one above the current
                //heightmap, this is because we can't traverse unless it is only one unit difference in height
                //skip if outside bounds or if height is more than one above current
                if(!heightMap.containsKey(c) || heightMap.get(c) > heightMap.get(node) + 1)
                    continue;
                int currentNode = nodeCosts.get(node) + 1;
                if(currentNode < nodeCosts.getOrDefault(c,Integer.MAX_VALUE)) {
                    nodeCosts.put(c,currentNode);
                    deQueue.add(c);
                }
            }
        }
        //this return statement is just to avoid an error, however it should never actually run since the
        //while loop will keep going until the end point is found
        return Integer.MAX_VALUE;
    }

    @Override
    public String p1() throws FileNotFoundException{
        //gets the input for day 12
        GetInputs input = new GetInputs(22,12);
        //refreshes the heightmap
        heightMap = new HashMap<>();
        //takes in each of the lines
        String[] lines = input.filetoArray();
        //creates empty Hyperduplexes for the start and end coordinate points
        HyperDuplex s = null;
        HyperDuplex e = null;
        //this parses in the input into the heightmap
        //while doing so it allocates where the start and the end points are located within the input
        //and takes not of theiry x and y coordinate points and allocates it into a hyper duplex
        for(int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for(int x = 0; x < line.length(); x++) {
                if(line.charAt(x) == 'S')
                    s = new HyperDuplex(x,y);
                else if(line.charAt(x) == 'E')
                    e = new HyperDuplex(x,y);
                else
                    //adds all the points into the heightmap with its respective character
                    heightMap.put(new HyperDuplex(x,y), (int) line.charAt(x));
            }
        }
        //puts  the start and end points into the heightmap and allocates them to start from lowest to highest
        heightMap.put(s,(int) 'a');
        heightMap.put(e,(int)'z');
        //calls the pathfinding method to find the path between the start and the end
        return Integer.toString(pathfind(s,e));
    }



    @Override
    public String p2() throws FileNotFoundException {
        //takes in the input for day 12
        GetInputs input = new GetInputs(22,12);
        //refreshes the heightmap
        heightMap = new HashMap<>();
        //takes in each line in the input and allocates them to a string array
        String[] lines = input.filetoArray();
        //creates empty points for the start and the end points
        HyperDuplex s = null;
        HyperDuplex e = null;
        //parses in the input and allocates all the points into the height map (except for the start and the end point)
        //howevre when  the start and the endpoint are found their hyperduplexes are taken into account
        for(int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for(int x = 0; x < line.length(); x++) {
                if(line.charAt(x) == 'S')
                    s = new HyperDuplex(x,y);
                else if(line.charAt(x) == 'E')
                    e = new HyperDuplex(x,y);
                else
                    heightMap.put(new HyperDuplex(x,y), (int) line.charAt(x));
            }
        }
        //we then add in the start and endpoints into the endpoint with
        // the smallest height and the biggest height respectively
        heightMap.put(s,(int) 'a');
        heightMap.put(e,(int)'z');
        //we then go through each of the possible locations and if the location is a 'a'
        //then it finds the pathfind between that one and the end
        //then the minimum return value from pathfind is found for all of the possible 'a' starting points
        int min = Integer.MAX_VALUE;
        for(HyperDuplex c : heightMap.keySet()) {
            if(heightMap.get(c) == 'a')
                min = Math.min(min,pathfind(c,e));
        }
        return Integer.toString(min);
    }
    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC12());
    }

}
