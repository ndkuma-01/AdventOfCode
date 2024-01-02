package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AoC8 implements DAYID {
    @Override
    public String p1() throws IOException {
        //takes in the input for day 8
        GetInputs input = new GetInputs(22,8);
        //creates a 2d array for each of the trees
        int[][] trees = new int[99][99];

        //parses in all the trees into the tree array
        for (int i = 0; i < trees.length; i++) {
            String s = input.nextLine();
            for (int j = 0; j < trees[0].length; j++) {
                trees[i][j] = Integer.parseInt(s.substring(j, j + 1));
            }
        }
        //presets the answer to take into account the perimeter of the 2d array
        int answer = (99 * 2) + (97 * 2);
        //goes through each of the inner trees and checks whether they are visible or not
        for (int i = 1; i < trees.length - 1; i++) {
            for (int j = 1; j < trees[0].length - 1; j++) {
                if (isVisible(trees, i, j)) {
                    answer++;
                }
            }
        }
        return String.valueOf(answer);
    }

    @Override
    public String p2() throws IOException {
        //takes in the input for day 8
        GetInputs input = new GetInputs(22,8);
        int[][] trees = new int[99][99];
        //parses in the trees
        for (int i = 0; i < trees.length; i++) {
            String s = input.nextLine();
            for (int j = 0; j < trees[0].length; j++) {
                trees[i][j] = Integer.parseInt(s.substring(j, j + 1));
            }
        }
        //finds the biggest scenic score of all the trees (that are inside the outer bounds of the 2d array)
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i < trees.length - 1; i++) {
            for (int j = 1; j < trees[0].length - 1; j++) {
                int n = getScenicScore(trees, i, j);
                answer = Math.max(answer, n);
            }
        }
        return String.valueOf(answer);
    }
    public static boolean isVisible(int[][] a, int i, int j) {
        //check up
        //to check if visible simply iterates up,down,left,and right
        //check up
        boolean vis = false;
        for (int check = i - 1; check >= 0; check--) {
            if (a[check][j] < a[i][j]) {
                vis = true;
            } else {
                vis = false;
                break;
            }
        }
        if (vis) {
            return true;
        }
        //check down
        vis = false;
        for (int check = i + 1; check < a.length; check++) {
            if (a[check][j] < a[i][j]) {
                vis = true;
            } else {
                vis = false;
                break;
            }
        }
        if (vis) {
            return true;
        }
        //check left
        vis = false;
        for (int check = j - 1; check >= 0; check--) {
            if (a[i][check] < a[i][j]) {
                vis = true;
            } else {
                vis = false;
                break;
            }
        }
        if (vis) {
            return true;
        }
        //check right
        vis = false;
        for (int check = j + 1; check < a[0].length; check++) {
            if (a[i][check] < a[i][j]) {
                vis = true;
            } else {
                vis = false;
                break;
            }
        }
        if (vis) {
            return true;
        }
        return false;
    }

    public static int getSSUP(int[][] a, int i, int j) {
        //finds the scenic score of the north direction
        int ss = 0;
        for (int check = i - 1; check >= 0; check--) {
            if (a[check][j] < a[i][j]) {
                ss++;
            } else if (a[check][j] == a[i][j]) {
                ss++;
                break;
            } else {
                break;
            }
        }
        return ss;
    }

    public static int getSSDOWN(int[][] a, int i, int j) {
        //finds the scencic score in the south direction
        int ss = 0;
        for (int check = i + 1; check < a.length; check++) {
            if (a[check][j] < a[i][j]) {
                ss++;
            } else if (a[check][j] == a[i][j]) {
                ss++;
                break;
            } else {
                break;
            }
        }
        return ss;
    }

    public static int getSSLEFT(int[][] a, int i, int j) {
        //finds the scenic score in the west direction
        int ss = 0;
        for (int check = j - 1; check >= 0; check--) {
            if (a[i][check] < a[i][j]) {
                ss++;
            } else if (a[i][check] == a[i][j]) {
                ss++;
                break;
            } else {
                break;
            }
        }
        return ss;
    }

    public static int getSSRIGHT(int[][] a, int i, int j) {
        //finds the scenic score of the west direction
        int ss = 0;
        for (int check = j + 1; check < a[0].length; check++) {
            if (a[i][check] < a[i][j]) {
                ss++;
            } else if (a[i][check] == a[i][j]) {
                ss++;
                break;
            } else {
                break;
            }
        }
        return ss;
    }

    public static int getScenicScore(int[][] a, int i, int j) {
        //gets the scenic score
        return (getSSDOWN(a, i, j) * getSSLEFT(a, i, j) * getSSUP(a, i, j) * getSSRIGHT(a, i, j));
    }

    public static void main(String[] args) throws IOException {
        RUNDAY.run(new AoC8());
    }
}
