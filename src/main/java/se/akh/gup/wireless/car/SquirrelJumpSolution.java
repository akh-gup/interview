package se.akh.gup.wireless.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SquirrelJumpSolution {

    private final int MAX_JUMP_LENGTH = 2;
    private int MAX_JUMPS;

    private APICaller api;

    HashMap<Integer, Integer> maxJumpsAtPositionMap = new HashMap<>();

    ArrayList<Integer> squirrelStepsSequence = new ArrayList<>();
    HashMap<Integer, Integer> nextBestPositionFromCurrentPositionMap = new HashMap<>();


    public SquirrelJumpSolution(APICaller api) {

        this.api = api;

        // You can initiate and calculate things here
        for (int i=0; i<api.getNumberOfBars(); i++) {
            calcMaxJumpsAtPosition(i);
        }
        MAX_JUMPS = calcMaxJumps();

        squirrelJumpSeries();
    }


    /***
     * Finds next possible steps for the current positions where squirrels can jump.
     *     Rule #1: MAX_JUMP_LENGTH should be considered.
     *     Rule #2: Next value should be less than current value.
     *
     * @param currentPosition   Current Position of Squirrel
     * @return                  All possible values possible
     */
    private ArrayList<Integer> nextPossibleSteps(int currentPosition){

        int currentVal = api.getBarHeight(currentPosition);
        ArrayList<Integer> possiblePositions = new ArrayList<>();

        for (int i=1; i <= MAX_JUMP_LENGTH; i++){

            if ((currentPosition-i) >= 0 && currentVal > api.getBarHeight(currentPosition-i)) {
                possiblePositions.add(currentPosition - i);
            }
        }

        for (int i=1; i <= MAX_JUMP_LENGTH; i++){
            if ((currentPosition+i) < api.getNumberOfBars() && currentVal > api.getBarHeight(currentPosition+i)) {
                possiblePositions.add(currentPosition + i);
            }
        }
        return possiblePositions;
    }


    /***
     *
     * Calculates what is the maximum distance squirrel can jump from the current positions.
     * @param position      Positions from where max jumps are calculated
     * @return              Maximum jumps from current position.
     */
    private int calcMaxJumpsAtPosition(int position){

        int jumps = 0;
        int maxJumps = Integer.MIN_VALUE;
        int nextBestPosition = -1;

        // If already calculated, no need to re-calculate the values
        if (maxJumpsAtPositionMap.containsKey(position)){
            return maxJumpsAtPositionMap.get(position);
        }

        // Fetch all possible positions, if Empty return 0;
        ArrayList<Integer> nextPosition = nextPossibleSteps(position);
        if (nextPosition.isEmpty()){

            maxJumpsAtPositionMap.put(position, jumps);
            nextBestPositionFromCurrentPositionMap.put(position, nextBestPosition);

            return jumps;
        }

        // Calculates maximum distance from current position recursively.
        for (int i: nextPosition){

            jumps = 1;
            jumps += calcMaxJumpsAtPosition(i);

            if (maxJumps < jumps){

                maxJumps = jumps;
                nextBestPosition = i;
            }
        }

        // Update maximum jumps possible at the current position so that duplicate calculations can be avoided.
        maxJumpsAtPositionMap.put(position, maxJumps);
        nextBestPositionFromCurrentPositionMap.put(position, nextBestPosition);

        return maxJumps;
    }


    /***
     * Calculates maximum jumps from the list of max jumps at each location.
     * @return              Maximum number of jumps from all locations.
     */
    private int calcMaxJumps(){

        int maxJumps = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry: maxJumpsAtPositionMap.entrySet()){

            if (maxJumps < entry.getValue()){
                maxJumps = entry.getValue();
            }
        }
        MAX_JUMPS = maxJumps;
        return maxJumps;
    }


    /***
     * Calculates the best position for squirrel to start.
     * @return          Maximum jumps possible from the location.
     */
    private int squirrelStartPos(){

        int initialPosition = -1;

        for (Map.Entry<Integer, Integer> entry: maxJumpsAtPositionMap.entrySet()){

            if (entry.getValue() == MAX_JUMPS){
                initialPosition = entry.getKey();
            }
        }
        return initialPosition;
    }


    /***
     * Stores all steps followed by squirrel to jump from initial position to last position in best case.
     *
     */
    private void squirrelJumpSeries(){

        int nextPos = squirrelStartPos();

        while(nextPos != -1){

            squirrelStepsSequence.add(nextPos);
            nextPos = nextBestPositionFromCurrentPositionMap.get(nextPos);
        }
    }


    /**
     * Return the maximum number of jumps possible.
     */
    public int maxNumberOfJumps() {

        // Write your code here
        return MAX_JUMPS;
    }


    /**
     * Return the squirrel's position at the step asked for. The initial
     * position is step zero, the position after the first jump is step 1 and
     * so forth.
     */
    public int positionAtStep(int step) {
        // Write your code here
        return squirrelStepsSequence.get(step);
    }
}
