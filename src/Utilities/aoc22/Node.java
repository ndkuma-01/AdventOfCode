package Utilities.aoc22;


import java.util.ArrayList;

public class Node {
    public Node parent;
    public ArrayList<Node> children = new ArrayList<Node>();
    public long totalFiles = 0;
    public String name;
    public Node(String s){
        name = s;
    }
}
