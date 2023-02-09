package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;
import Utilities.aoc22.Duplex20;
import Utilities.aoc22.Duplex21;

import java.io.FileNotFoundException;

public class AoC20 implements DAYID {
    //this solution uses two different types of Duplex types called Duplex 20 and Duplex 21, they are almost identical
    //this was coded before the making of the Hyperduplex, and the hyperduplex could have hastened the coding and execution
    //of this code

    @Override
    public String p1() throws FileNotFoundException {
        //the input is gathered from day 20
        GetInputs input = new GetInputs(22,20);
        //creates an array for all of the numbers held in the input
        Duplex20[] nums = new Duplex20[5000];
        int counter = 0;
        Duplex20[] numsCopy = new Duplex20[5000];
        //iterates through all the lines
        while (input.hasLines()) {
            Duplex20 temp = new Duplex20(counter, Integer.parseInt(input.nextLine()));
            nums[counter] = temp;
            numsCopy[counter] = temp;
            counter++;
        }
        //the way that part 1 is solved is by making sure that every entry/input has a unique id that tells us the order of the
        //inputs
        //that way we don't iterate over inputs multiple times over


        int length = nums.length;
        counter = 0;


        int num1 = 0;
        int num2 = 0;
        int idx = 0;
        //iterates through the nums
        for (int iterator = 0; iterator < numsCopy.length; iterator++) {
            num1 = numsCopy[iterator].getNum1();
            num2 = numsCopy[iterator].getNum2();

            for (idx = 0; idx < length; idx++) {
                if (nums[idx].getNum1() == num1) {
                    break;
                }
            }

            if (num2 < 0) {
                int cur = idx;
                //we perform the swaps for the amount of times that is necessary
                //and we % by the length in order to loop over
                for (int placeholder = 0; placeholder < (-1 * num2); placeholder++) {
                    int temp = (cur - 1);
                    nums = swap(nums, cur, ((temp % length) + length) % length);
                    cur = (((temp % length) + length) % length);
                }
                continue;
            }

            if (num2 > 0) {
                int cur = idx;
                for (int placeholder = 0; placeholder < num2; placeholder++) {
                    nums = swap(nums, cur, (cur + 1) % length);
                    cur = (cur + 1) % length;
                }
            }
        }
        //an array list of grove points is created in order to be checked upon later on
        int[] grovePoints = {1000, 2000, 3000};

        int answer = 0;
        int zero = 0;
        //finds where the zero is located in the newly mixed file
        for (zero = 0; zero < length; zero++) {
            if (nums[zero].getNum2() == 0) {
                break;
            }
        }
        //embodies each of the grove points
        for (int c : grovePoints) {
            //adds the integers at those grove points from the zero'th position and having modded by the length
            answer += nums[(zero + c) % length].getNum2();
        }

        return String.valueOf(answer);
    }

    public static Duplex20[] swap(Duplex20[] nums, int a, int b) {
        //an exceedingly simple swap function :)
        Duplex20 temp = nums[a];
//        System.out.println("Before: " + nums[a] +" " + nums[b]);

        nums[a] = nums[b];
        nums[b] = temp;
//        nums.remove(a);
//        if(a == 0){
//            nums.addFirst(0,nums.get(b));
//        }else{
//            nums.add(a,nums.get(b));
//        }
//        nums.remove(b);
//        if(b == nums.size()){
//            nums.addLast(temp);
//        }else{
//            nums.add(b, temp);
//
//        }
//        System.out.println("After: " + nums[a] +" " + nums[b]);

        return nums;
    }

    @Override
    public String p2() throws FileNotFoundException {
        //takes the input for day 20
        //this is essentially the exact same as the first part
        //except we first multiply all the values by the decrpyt key
        GetInputs input = new GetInputs(22,20);
        Duplex21[] nums = new Duplex21[5000];
        int counter = 0;
        Duplex21[] numsCopy = new Duplex21[5000];
        int DECRYPYT_KEY = 811589153;

        while (input.hasLines()) {
            Duplex21 temp = new Duplex21(counter, (long) Integer.parseInt(input.nextLine()) * DECRYPYT_KEY);
            nums[counter] = temp;
            numsCopy[counter] = temp;
            counter++;
        }

        int length = nums.length;
        counter = 0;


        int num1 = 0;
        long num2 = 0;
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            for (int iterator = 0; iterator < numsCopy.length; iterator++) {
                num1 = numsCopy[iterator].getNum1();
                num2 = numsCopy[iterator].getNum2();

                for (idx = 0; idx < length; idx++) {
                    if (nums[idx].getNum1() == num1) {
                        break;
                    }
                }
                //this makes the process deal with smaller numbers while preserving the modding
                if (num2 > 0) {
                    num2 %= (length - 1);
                } else {
                    num2 = (num2 % (length - 1) + (length - 1)) % (length - 1);
                }

                if (num2 > 0) {
                    int cur = idx;
                    for (int placeholder = 0; placeholder < num2; placeholder++) {
                        nums = swap2(nums, cur, (cur + 1) % length);
                        cur = (cur + 1) % length;
                    }
                }
            }
        }
        int[] grovePoints = {1000, 2000, 3000};

        long answer = 0;
        int zero = 0;
        for (zero = 0; zero < length; zero++) {
            if (nums[zero].getNum2() == 0) {
                break;
            }
        }
        for (int c : grovePoints) {
            answer += nums[(zero + c) % length].getNum2();
        }

        return String.valueOf(answer);


    }


    public static Duplex21[] swap2(Duplex21[] nums, int a, int b) {
        Duplex21 temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
        return nums;
    }

    public static void main(String[] args) throws FileNotFoundException {
        RUNDAY.run(new AoC20());
    }
}
