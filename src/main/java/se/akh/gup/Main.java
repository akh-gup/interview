package se.akh.gup;

import se.akh.gup.wireless.car.APICaller;
import se.akh.gup.wireless.car.SquirrelJumpSolution;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){

        APICaller apiCaller = new APICaller();
        SquirrelJumpSolution squirrelJump = new SquirrelJumpSolution(apiCaller);

        System.out.println("All possible values are: " + Arrays.toString(apiCaller.array));

        int maxJumps = squirrelJump.maxNumberOfJumps();
        System.out.println("Maximum Number of Jumps: " + squirrelJump.maxNumberOfJumps());

        for (int i=0; i<= maxJumps; i++){
            System.out.println("Step " + i + ": " + squirrelJump.positionAtStep(i));
        }
    }
}
