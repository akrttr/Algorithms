import java.time.LocalTime;
import java.util.Arrays;

public class Assignment implements Comparable {
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isMaellard() {
        return this.maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        String [] s_time = start.split(":");
        int s_hour = Integer.parseInt(s_time[0]);
        int s_minute = Integer.parseInt(s_time[1]);

        LocalTime startTime = LocalTime.of(s_hour,s_minute);
        LocalTime finishTime = startTime.plusHours(duration);
        String finito = finishTime.toString();

        return finito;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {

        if(this.maellard == true){
            double weight = (double)this.importance*1001/this.duration;
            return weight;
        }
        else {
            double weight = (double)this.importance/this.duration;
            return weight;
        }

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {


        String [] current = this.getFinishTime().split(":");         // Splitting times
        int currentHour = Integer.parseInt(current[0]);                    // Getting hours
        int currentMinute = Integer.parseInt(current[1]);                  // Getting minutes

        String [] obj = ((Assignment)o).getFinishTime().split(":"); // Splitting times
        int objHour = Integer.parseInt(obj[0]);                           // Getting hours
        int objMinute = Integer.parseInt(obj[1]);                         // Getting hours


        if(currentHour > objHour) {                                       // Comparing times in nested if structure
            return 1;
        }
        else if (currentHour < objHour) {
            return -1;
        }
        else {

            return Integer.compare(currentMinute,objMinute);
        }

    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString() {
        return "Assignment{name='"+this.getName()+"', start='"+this.getStartTime()+"', duration="+this.getDuration()+", importance="+this.getImportance()+", maellard="+this.isMaellard()+", finish='"+this.getFinishTime()+"', weight="+this.getWeight()+"}";
    }
}