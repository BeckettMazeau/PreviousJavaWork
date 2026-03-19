package StepTracker;

public class StepTracker {
    private int activeSteps;
    private int totalSteps;
    private int activeDays;
    private int days;
    public StepTracker(int minNumActive){
        activeSteps = minNumActive;
        days = 0;
        activeDays = 0;
        totalSteps = 0;
    }
    public void addDailySteps(int steps){
        if (steps>=activeSteps){
            activeDays++;
        }
        totalSteps += steps;
        days++;
    }
    public int getActiveDays(){return activeDays;}
    public double getAverageSteps(){if(days != 0){return (double)totalSteps/days;}
        return 0;
    }
}
