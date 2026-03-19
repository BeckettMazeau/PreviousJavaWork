package Gradebook2;


import java.util.ArrayList;
import java.util.List;

public class Gradebook {
    private List<Integer> scores = new ArrayList<>();

    public Gradebook(){}

    void addScore (int newScore){
        scores.add(newScore);
    }
    double getAvgScore(){
        double scoreSum = 0;
        for (int i = 0; i < scores.size(); i++) {
            scoreSum += scores.get(i);
        }
        return scoreSum / scores.size();
    }
    int getMaxScore(){
        int maxScore = 0;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i)>maxScore){
                maxScore = scores.get(i);
            }
        }
        return maxScore;
    }
    int getMinScore(){
        int minScore = 1000000;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i)<minScore){
                minScore = scores.get(i);
            }
        }
        return minScore;
    }


}