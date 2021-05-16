import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    /**
     * @throws IllegalArgumentException when the given array is empty
     */
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {

        // Should be instantiated with an Assignment array
        // All the properties of this class should be initialized here

        if (assignmentArray.length == 0) {          //Empty File Check
            throw new IllegalArgumentException();
        }

        this.assignmentArray = assignmentArray;
        this.C = new Integer[assignmentArray.length];
        calculateC();                               // initialize C array
        max = new Double[assignmentArray.length];
        calculateMax(assignmentArray.length - 1);
        this.solutionDynamic = new ArrayList<>();

        this.solutionGreedy = new ArrayList<>();

    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
    private int binarySearch(int index) {
        int low = 0;            //leftmost items
        int high = assignmentArray.length;      //rightmost item


        while (low < high) {        //while loop
            int middle = (high + low) / 2;      //calculating middle value
            String[] startTimes = this.assignmentArray[index].getStartTime().split(":");        // Get start time and split it
            String[] finishTimes = this.assignmentArray[middle].getFinishTime().split(":");     // Get finish time and split it

            if (Integer.parseInt(finishTimes[0]) > Integer.parseInt(startTimes[0])) {               //Nested if structure for comparing items and return appropriate value
                high = middle;
            } else {
                if (Integer.parseInt(finishTimes[0]) == Integer.parseInt(startTimes[0])) {
                    if (Integer.parseInt(finishTimes[1]) > Integer.parseInt(startTimes[1])) {
                        high = middle;
                    } else {
                        low = middle + 1;
                    }
                } else low = middle + 1;
            }
        }
        return high - 1;
    }


    /**
     * {@link #C} must be filled after calling this method
     */
    public void calculateC() {

        for (int i = 0; i < assignmentArray.length; i++) {
            C[i] = binarySearch(i);         // Calculate binary search for every i in assignment length and append results into C array
        }

    }


    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
        findSolutionDynamic(assignmentArray.length - 1);
        Collections.reverse(solutionDynamic);       // Reverse operation
        return solutionDynamic;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int i) {
        System.out.println("findSolutionDynamic(" + i + ")");
        if (assignmentArray[i].getWeight() + (C[i] >= 0 ? max[C[i]] : 0.0) >= max[i]) {
            solutionDynamic.add(assignmentArray[i]);
            System.out.println("Adding " + assignmentArray[i] + " to the dynamic schedule");
            if (C[i] >= 0 && i!=0)
                findSolutionDynamic(this.C[i]);
        } else
            findSolutionDynamic(i - 1);
    }

    /**
     * {@link #max} must be filled after calling this method
     */

    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    private Double calculateMax(int i) {
        if (i >= 0) {
            if (i == 0) {
                System.out.println("calculateMax(" + i + "): Zero");
                max[i] = Math.max(assignmentArray[i].getWeight() + calculateMax(C[i]), calculateMax(i - 1));
            }
            else if (max[i] == null) {
                System.out.println("calculateMax(" + i + "): Prepare");
                max[i] = Math.max(assignmentArray[i].getWeight() + calculateMax(C[i]), calculateMax(i - 1));
            } else {
                System.out.println("calculateMax(" + i + "): Present");
            }
            return max[i];
        } else {
            return 0.0;
        }
    }

    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments

     */

    /*
     *
     * This function is for scheduling in greedy way.
     *
     * */
    public ArrayList<Assignment> scheduleGreedy() {
        solutionGreedy.add(assignmentArray[0]);
        System.out.println("Adding " + assignmentArray[0] + " to the greedy schedule");
        for (Assignment assignment : assignmentArray) {
            String[] times = assignment.getStartTime().split(":");
            String[] nextTime = solutionGreedy.get(solutionGreedy.size() - 1).getFinishTime().split(":");
            if (Integer.parseInt(times[0]) > Integer.parseInt(nextTime[0])) {
                solutionGreedy.add(assignment);
                System.out.println("Adding " + assignment + " to the greedy schedule");
            } else if (Integer.parseInt(times[0]) == Integer.parseInt(nextTime[0])) {
                if (Integer.parseInt(times[1]) >= Integer.parseInt(nextTime[1])) {
                    solutionGreedy.add(assignment);
                    System.out.println("Adding " + assignment + " to the greedy schedule");
                }
            }
        }

        return solutionGreedy;
    }
}