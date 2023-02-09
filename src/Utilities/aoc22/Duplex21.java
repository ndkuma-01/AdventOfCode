package Utilities.aoc22;

public class Duplex21 {
    int num1;
    long num2;
    public Duplex21(int a, long b){
        num1 = a;
        num2 = b;
    }

    public int getNum1() {
        return num1;
    }

    public long getNum2() {
        return num2;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    @Override
    public String toString() {
        return "(" + num1 + ", " + num2 + "), ";
    }
}
