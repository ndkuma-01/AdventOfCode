package javafiles.aoc22;

import Utilities.General.DAYID;
import Utilities.General.GetInputs;
import Utilities.General.RUNDAY;

import java.io.FileNotFoundException;

public class AoC1 implements DAYID {
    @Override
    public String p1() throws FileNotFoundException {
        //Grabs the input for day 1
        GetInputs INPUT1 = new GetInputs(22,1);
        //creates a variable for holding the top three biggest number of calories held
        int big = Integer.MIN_VALUE, big2=Integer.MIN_VALUE, big3 = Integer.MIN_VALUE;
        int temp =0;
        //this while loop iterates through each line
        while(INPUT1.hasLines()){
            //takes in a single line
            String calorie = INPUT1.nextLine();
            if(calorie.equals("") ){
                //once all the calories have been accounted for then we can figure out where it lies in the top numbers of calories
                if(big<temp){
                    //if calories is bigger then the biggest then it will replace the biggest, and shift the biggest to the second biggest
                    //then the second biggest to the third biggest
                    //it will dispose the third biggest value
                    int placeholder = big2;
                    big2= big;
                    big3=placeholder;
                    big = temp;
                }else if(big2 < temp){
                    //shifts the current second biggest to the third biggest and disposes the third biggest
                    big3= big2;
                    big2 = temp;
                }else if(big3<temp){
                    //simply sets the third biggest to the new temp
                    big3 = temp;
                }
                temp=0;
                continue;
            }
            //sums up the calories until a new elf is encountered
            temp +=  Integer.parseInt(calorie);

        }
        //returns the biggest value
        return String.valueOf(big);
    }
    @Override
    public String p2() throws FileNotFoundException {
        //this is the exact same as the first part however instead of just returning the top 1, it sums and returns the sum of the top 3 see line
        //78
        GetInputs INPUT2 = new GetInputs(22,1);
        int big = Integer.MIN_VALUE, big2=Integer.MIN_VALUE, big3 = Integer.MIN_VALUE;
        int temp =0;
        while(INPUT2.hasLines()){
            String calorie = INPUT2.nextLine();
            if(calorie.equals("") ){
                if(big<temp){
                    int placeholder = big2;
                    big2= big;
                    big3=placeholder;
                    big = temp;
                }else if(big2 < temp){
                    big3= big2;
                    big2 = temp;
                }else if(big3<temp){
                    big3 = temp;
                }
                temp=0;
                continue;
            }
            temp +=  Integer.parseInt(calorie);
        }
        int ans = big + big2 + big3;
        //the variable ans has the top 3 summed together and gets returned as the answer
        return String.valueOf(ans);
    }
    public static void main(String[] args) throws FileNotFoundException {
        //this simply will run the  whole program, as declared by the RUNDAY class
        //it also prints out the elapsed time it takes for each part too run
        RUNDAY.run(new AoC1());
    }
}
