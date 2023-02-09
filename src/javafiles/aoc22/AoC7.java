package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.Node;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AoC7 implements DAYID {
    //the solution for this problem heavily relies on recursion

    public long smallestMax(Node n, long min) {
        //finds the biggest number that is still under the threshold (the minimum)
        long small = Long.MAX_VALUE;
        //goes through each of its children and finds the smallest max for those (recursion)
        for (Node o : n.children) {
            small = Math.min(small, smallestMax(o, min));
        }
        //finds the totalsize when it is under the minimum
        if (totSize(n) > min)
            small = Math.min(small, totSize(n));

        return small;
    }

    public long totSize(Node n) {
        //goes through each of the children in order to find the total size of a given node
        long total = 0;
        for (Node o : n.children) {
            total += totSize(o);
        }
        total += n.totalFiles;

        return total;
    }

    public long sizeBIG(Node n) {
        //responsible for finding the size of the root
        long total = 0;
        for (Node o : n.children) {
            total += sizeBIG(o);
        }
        if (totSize(n) < 100001) {
            total += totSize(n);
        }
        return total;
    }

    @Override
    public String p1() throws FileNotFoundException {
        //gets the input for day 7
        GetInputs input = new GetInputs(22,7);
        //creates the intial root node
        Node root = new Node("/");
        //creates a 'parsor' variable of sorts that traverses the nodes
        Node current = root;
        //creates an array list fors each of the lines
        ArrayList<String> lines = new ArrayList<String>();
        while (input.hasLines()) {
            lines.add(input.nextLine());

        }
        //goes through each line
        for (int i = 0; i < lines.size(); i++) {
            String currLine = lines.get(i);
            //we don't care about whever the dir command is called, or when it ends with /
            //so we will skip those lines
            if (currLine.startsWith("dir") || currLine.endsWith("/")) {
                continue;
            }
            //we then take the path given and split it into an array of parts, split by spaces
            String[] path = currLine.split(" ");
            //isolates when it is a command
            if (path[0].equals("$")) {
                //finds when we are creating/accessing a directory
                if (path[1].equals("cd")) {
                    //figures out whether we are popping out or not
                    if (path[2].equals("..")) {
                        //traverses the parser back a step to its parent
                        current = current.parent;
                    } else {
                        //creates a new child and adds it to the children of the parsor node
                        Node child = new Node(path[2]);
                        current.children.add(child);
                        child.parent = current;
                        //moves the parser to the child
                        current = child;
                    }
                }
            } else {
                //adds new file
                current.totalFiles += Long.parseLong(path[0]);
            }
        }
        //finds the size of the root
        return Long.toString(sizeBIG(root));
    }

    @Override
    public String p2() throws FileNotFoundException {
        //accesses the input for day 7
        GetInputs input = new GetInputs(22,7);
        //the first part of the second problem is the exact same as the first
        Node root = new Node("/");
        Node current = root;
        ArrayList<String> lines = new ArrayList<String>();
        while (input.hasLines()) {
            lines.add(input.nextLine());

        }
        for (int i = 0; i < lines.size(); i++) {
            String currLine = lines.get(i);
            if (currLine.startsWith("dir") || currLine.endsWith("/")) {
                continue;
            }

            String[] path = currLine.split(" ");
            if (path[0].equals("$")) {
                if (path[1].equals("cd")) {
                    if (path[2].equals("..")) {
                        current = current.parent;
                    } else {
                        Node child = new Node(path[2]);
                        current.children.add(child);
                        child.parent = current;
                        current = child;
                    }
                }
            } else {
                current.totalFiles += Long.parseLong(path[0]);
            }
        }
        //here is where the similarities end between the first and the second
        //we calculate the threshold
        long minimum = 30000000 - (70000000 - totSize(root));
        //and call a function to find the biggest one that is still under the threshold
        return Long.toString(smallestMax(root, minimum));
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC7());
    }
}
