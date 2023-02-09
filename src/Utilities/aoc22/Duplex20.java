package Utilities.aoc22;

public class Duplex20 {
        int num1;
        int num2;
        public Duplex20(int a, int b){
            num1 = a;
            num2 = b;
        }

        public int getNum1() {
            return num1;
        }

        public int getNum2() {
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


